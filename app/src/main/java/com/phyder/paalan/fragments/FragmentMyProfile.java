package com.phyder.paalan.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.RoundedImageView;
import com.phyder.paalan.utils.TextViewOpenSansRegular;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Attr;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FragmentMyProfile extends Fragment {

    private final static String TAG = FragmentMyProfile.class.getCanonicalName();
    private final static String IS_REGISTERED = "y";
    private final static String IS_NEWS_LETTER = "cmss";

    final static int IMG_RESULT = 1;
    final int CAMERA_REQUEST = 1888;
    private RoundedImageView profileImage;
    private TextViewOpenSansRegular txtUserName;

    private ButtonOpenSansSemiBold btnUpdateProfile;
    private EditTextOpenSansRegular edtRole, edtRegistrationNo, edtFacebookLink, edtTwitterLink, edtLinkedInLink,
            edtWebsiteLink, edtPresenceArea;
    private String strRole, strRegNo, strfblink, strlinkedin, strWeblink, strtwitter, strPresenceArea,strEncodedDp = "";

    private PreferenceUtils pref;
    private DBAdapter dbAdapter;
    private Cursor cursor;
    private boolean isDoingUpdateDp = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_profile, null, false);
        initializingComponents(view);
        populatingData();
        clickingEventFire();
        return view;
    }



    private void initializingComponents(View view) {

        pref = new PreferenceUtils(getActivity());
        dbAdapter = new DBAdapter(getActivity());

        edtRole = (EditTextOpenSansRegular) view.findViewById(R.id.edit_role);
        edtRegistrationNo = (EditTextOpenSansRegular) view.findViewById(R.id.registration_no);
        edtFacebookLink = (EditTextOpenSansRegular) view.findViewById(R.id.fb_link);
        edtLinkedInLink = (EditTextOpenSansRegular) view.findViewById(R.id.linkedin_link);
        edtWebsiteLink = (EditTextOpenSansRegular) view.findViewById(R.id.website_link);
        edtTwitterLink = (EditTextOpenSansRegular) view.findViewById(R.id.twitter_link);
        edtPresenceArea = (EditTextOpenSansRegular) view.findViewById(R.id.presence_area);
        btnUpdateProfile = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_profile_update);
        profileImage = (RoundedImageView) view.findViewById(R.id.imageview_dp_image);
        txtUserName = (TextViewOpenSansRegular) view.findViewById(R.id.txtProfileName);

    }



    private void populatingData(){

        dbAdapter.open();
        cursor = dbAdapter.getProfile();
        if(cursor !=null)
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    edtRole.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_ROLE)));
                    edtRegistrationNo.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_REGISTER_NO)));
                    edtFacebookLink.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_FB_LINK)));
                    edtLinkedInLink.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_LINKED_IN_LINK)));
                    edtWebsiteLink.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_WEB_LINK)));
                    edtTwitterLink.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_TWITTER_LINK)));
                    edtPresenceArea.setText(cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_PRESENCE_AREA)));


                    if(!isDoingUpdateDp)
                        strEncodedDp = cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_IMAGE));

                    if(!strEncodedDp.isEmpty() && !strEncodedDp.contains("http://")){
                        profileImage.setImageBitmap(CommanUtils.decodeBase64(strEncodedDp));
                    }else if(!strEncodedDp.isEmpty() && strEncodedDp.contains("http://")){
                        Picasso.with(getActivity())
                                .load(strEncodedDp)
                                .placeholder(R.drawable.unknown)   // optional
                                .error(R.drawable.unknown)      // optional
                                .into(profileImage);
                    }

                }while(cursor.moveToNext());
            }

        dbAdapter.close();

        if (pref.getUserName() != null) {
            txtUserName.setText(pref.getUserName());
        }


    }


    private void clickingEventFire() {

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(" Select Profile");
                alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isDoingUpdateDp = true;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    }
                });
                alertDialog.setNegativeButton("Gallary", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isDoingUpdateDp = true;
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, IMG_RESULT);
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strRole = edtRole.getText().toString();
                strRegNo = edtRegistrationNo.getText().toString();
                strfblink = edtFacebookLink.getText().toString();
                strlinkedin = edtLinkedInLink.getText().toString();
                strWeblink = edtWebsiteLink.getText().toString();
                strtwitter = edtTwitterLink.getText().toString();
                strPresenceArea = edtPresenceArea.getText().toString();

                if(strRole.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_role));
                }else if(strRegNo.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_regNo));
                }else if(strPresenceArea.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_presence));
                }else{
                    getRetrofitCall();
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileImage.setImageBitmap(photo);
                strEncodedDp = CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(photo));

            } else if (requestCode == IMG_RESULT || requestCode == IMG_RESULT) {

                try {
                    if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                            && null != data) {
                        Uri URI = data.getData();
                        String[] FILE = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(URI,
                                FILE, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(FILE[0]);
                        strEncodedDp = cursor.getString(columnIndex);
                        profileImage.setImageBitmap(BitmapFactory
                                .decodeFile(strEncodedDp));

                        strEncodedDp = CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(BitmapFactory
                                .decodeFile(strEncodedDp)));
                        cursor.close();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                            .show();
                }
            }
    }


    public void getRetrofitCall(){

        if(NetworkUtil.isInternetConnected(getActivity())){
            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());

            OrganisationReqProfile reqProfile = OrganisationReqProfile.get(pref.getOrgId(), strRole,
                    CommanUtils.getImeiNo(getActivity()),IS_NEWS_LETTER, IS_REGISTERED,
                    strRegNo,strEncodedDp, strfblink, strlinkedin, strWeblink, strtwitter, strPresenceArea);


            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrganizationResProfile> resProfile = mPaalanServices.orgProfile(reqProfile);

            resProfile.enqueue(new Callback<OrganizationResProfile>() {
                @Override
                public void onResponse(Call<OrganizationResProfile> call, Response<OrganizationResProfile> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();
                            dbAdapter.deleteProfile();
                            dbAdapter.insertProfile(strEncodedDp,strRole,strRegNo,strfblink,strlinkedin,strWeblink,strtwitter,
                                    strPresenceArea);
                            dbAdapter.close();
                            isDoingUpdateDp = false;
                            ActivityFragmentPlatform.getProfileUpdate(getActivity());
                            Toast.makeText(getActivity(), getResources().getString(R.string.profile_up_to_date), Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrganizationResProfile> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.profile));
    }
}
