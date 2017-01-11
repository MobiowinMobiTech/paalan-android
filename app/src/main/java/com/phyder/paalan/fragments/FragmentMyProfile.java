package com.phyder.paalan.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.activity.organization.OrganizationProfile;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class FragmentMyProfile extends Fragment {
    final static int IMG_RESULT = 1;
    final int CAMERA_REQUEST = 1888;
    private ImageView profileImage;
    String ImageDecode;

    Intent intent;
    Button btnUpdateProfile;
    EditText edtOrgID, edtRole, edtRegistrationNo, edtFacebookLink, edtTwitterLink, edtLinkedInLink, edtWebsiteLink, edtPresenceArea;
    String[] FILE;
    String strOrgId, strRole, strRegNo, strfblink, strlinkedin, strWeblink, strtwitter, strPresenceArea, strImeiNo, isRegistered, isnewsletter,
            dpImage;


    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_profile, container, false);

        edtOrgID = (EditText) view.findViewById(R.id.edt_org_id);
        edtRole = (EditText) view.findViewById(R.id.edit_role);
        edtRegistrationNo = (EditText) view.findViewById(R.id.registration_no);
        edtFacebookLink = (EditText) view.findViewById(R.id.fb_link);
        edtLinkedInLink = (EditText) view.findViewById(R.id.linkedin_link);
        edtWebsiteLink = (EditText) view.findViewById(R.id.website_link);
        edtTwitterLink = (EditText) view.findViewById(R.id.twitter_link);
        edtPresenceArea = (EditText) view.findViewById(R.id.presence_area);
        btnUpdateProfile = (Button) view.findViewById(R.id.btn_profile_update);

        strOrgId = edtOrgID.getText().toString();
        strRole = edtRole.getText().toString();
        strRegNo = edtRegistrationNo.getText().toString();
        strfblink = edtFacebookLink.getText().toString();
        strlinkedin = edtLinkedInLink.getText().toString();
        strWeblink = edtWebsiteLink.getText().toString();
        strtwitter = edtTwitterLink.getText().toString();
        strPresenceArea = edtPresenceArea.getText().toString();
        strImeiNo = "9865465461158";
        isRegistered = "y";
        isnewsletter = "cmss";

        Social social = new Social();
        dpImage = social.DP_IMG;

        Log.d(TAG, "onCreateView: " + strOrgId + "\n" + strRole + "\n" + strRegNo + "\n" + strfblink + "\n" + strlinkedin + "\n" + strWeblink + "\n" + strtwitter + "\n" + strPresenceArea + "\n");

        profileImage = (ImageView) view.findViewById(R.id.imageview_dp_image);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(" Select Profile");
                alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    }
                });
                alertDialog.setNegativeButton("Gallary", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(Intent.ACTION_PICK,
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
                Toast.makeText(getActivity(), "Create Profile Update Service.", Toast.LENGTH_SHORT).show();

                Device.newInstance(getActivity());

                OrganisationReqProfile reqProfile = OrganisationReqProfile.get(strOrgId, strRole, strImeiNo, isnewsletter, isRegistered,
                        strRegNo, dpImage, strfblink, strlinkedin, strWeblink, strtwitter, strPresenceArea);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrganizationResProfile> profileCall = mPaalanServices.orgProfile(reqProfile);
                profileCall.enqueue(new Callback<OrganizationResProfile>() {
                    @Override
                    public void onResponse(Call<OrganizationResProfile> call, Response<OrganizationResProfile> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("success")) {
                                Log.d(TAG, "onResponse: " + response.body().getData());
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_dashboard, new FragmentMyProfile()).commit();
                            } else if (response.body().getStatus().equals("error")) {
                                Log.d(TAG, "onResponse: " + response.body().getMessage());
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<OrganizationResProfile> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            profileImage.setImageBitmap(photo);
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
                    ImageDecode = cursor.getString(columnIndex);
                    profileImage.setImageBitmap(BitmapFactory
                            .decodeFile(ImageDecode));
                    cursor.close();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

}


//

