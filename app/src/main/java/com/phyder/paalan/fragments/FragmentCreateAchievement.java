package com.phyder.paalan.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentCreateAchievement extends Fragment{

    private static final String TAG = FragmentCreateAchievement.class.getCanonicalName();
    private EditTextOpenSansRegular edtName,edtTitle,edtSubTitle,edtDescription,edtOthers;
    private ImageView imgAchievementFirst,imgAchievementSecond,imgAchievementThird,imgAchievementForth;
    private ButtonOpenSansSemiBold btnSubmit;

    final static int PICK_IMAGE_FROM_GALARY = 1;

    private String imgDecodableFirst = "",imgDecodableSecond = "",imgDecodableThird = "",imgDecodableForth = "";
    private String achievementID = "",strName = "",strTitle = "",strSubTitle = "",strDescription = "",strOthers = "";
    private ArrayList<String> listOfImages;
    private int imgAchievementStatus = 1;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private PreferenceUtils pref;
    private DBAdapter dbAdapter;
    private boolean shouldBeUpdated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_create_achievement,null,false);
            init(view);
            clickEventFire();
        return view;
    }

    private void init(View view) {

        pref = new PreferenceUtils(getActivity());
        dbAdapter = new DBAdapter(getActivity());
        edtName = (EditTextOpenSansRegular) view.findViewById(R.id.edt_name);
        edtTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_title);
        edtSubTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOthers = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);

        imgAchievementFirst = (ImageView) view.findViewById(R.id.imgFirst);
        imgAchievementSecond = (ImageView) view.findViewById(R.id.imgSecond);
        imgAchievementThird = (ImageView) view.findViewById(R.id.imgThird);
        imgAchievementForth = (ImageView) view.findViewById(R.id.imgForth);

        btnSubmit = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_submit);

        listOfImages =new ArrayList<String>();

        Bundle bundle = getArguments();

        if(bundle!=null) {

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            achievementID = bundle.getString("ID");
            edtName.setText(bundle.getString("NAME"));
            edtTitle.setText(bundle.getString("TITLE"));
            edtSubTitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOthers.setText(bundle.getString("OTHER"));

            imgDecodableFirst = bundle.getString("IMAGE1");
            imgDecodableSecond = bundle.getString("IMAGE2");
            imgDecodableThird = bundle.getString("IMAGE3");
            imgDecodableForth = bundle.getString("IMAGE4");


            if (imgDecodableFirst != null && !imgDecodableFirst.isEmpty()) {
                loadAchievementAttachments(imgAchievementFirst,imgDecodableFirst);
                imgAchievementSecond.setVisibility(View.VISIBLE);
            }
            if (imgDecodableSecond != null && !imgDecodableSecond.isEmpty()) {
                loadAchievementAttachments(imgAchievementSecond,imgDecodableSecond);
                imgAchievementThird.setVisibility(View.VISIBLE);
            }
            if (imgDecodableThird != null && !imgDecodableThird.isEmpty()) {
                loadAchievementAttachments(imgAchievementThird,imgDecodableThird);
                imgAchievementForth.setVisibility(View.VISIBLE);
            }
            if (imgDecodableForth != null && !imgDecodableForth.isEmpty()) {
                loadAchievementAttachments(imgAchievementForth,imgDecodableForth);
            }

        }

        btnSubmit.setText(shouldBeUpdated ? getResources().getString(R.string.Update) :
                getResources().getString(R.string.Create));
    }


    private void clickEventFire() {

        imgAchievementFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAchievementStatus = 1;
                if(requestPermission()){
                    pickImage();
                }

            }
        });


        imgAchievementSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAchievementStatus = 2;
                if(requestPermission()){
                    pickImage();
                }
            }
        });



        imgAchievementThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAchievementStatus = 3;
                if(requestPermission()){
                    pickImage();
                }
            }
        });


        imgAchievementForth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAchievementStatus = 4;
                if(requestPermission()){
                    pickImage();
                }
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strName = edtName.getText().toString();
                strTitle = edtTitle.getText().toString();
                strSubTitle = edtSubTitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strOthers = edtOthers.getText().toString();

                if(strName.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_name));
                }else if(strTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_title));
                } else if(strSubTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_sub_title));
                } else if(strDescription.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_description));
                } else if(strOthers.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_others));
                } else {

                    listOfImages.add(imgDecodableFirst);
                    listOfImages.add(imgDecodableSecond);
                    listOfImages.add(imgDecodableThird);
                    listOfImages.add(imgDecodableForth);
                    getRetrofitCall();

                }
            }
        });
    }


    public void pickImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALARY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == PICK_IMAGE_FROM_GALARY && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                if(imgAchievementStatus == 1){

                    imgDecodableFirst = getImageData(cursor,columnIndex);
                    loadAchievementAttachments(imgAchievementFirst,imgDecodableFirst);
                    imgAchievementSecond.setVisibility(View.VISIBLE);

                } else if(imgAchievementStatus == 2){

                    imgDecodableSecond = getImageData(cursor,columnIndex);
                    loadAchievementAttachments(imgAchievementSecond,imgDecodableSecond);
                    imgAchievementThird.setVisibility(View.VISIBLE);

                } else if(imgAchievementStatus == 3){

                    imgDecodableThird = getImageData(cursor,columnIndex);
                    loadAchievementAttachments(imgAchievementThird,imgDecodableThird);
                    imgAchievementForth.setVisibility(View.VISIBLE);

                } else if(imgAchievementStatus == 4){

                    imgDecodableForth = getImageData(cursor,columnIndex);
                    loadAchievementAttachments(imgAchievementForth,imgDecodableForth);
                }
                cursor.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                    .show();;
        }

    }


    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE },
                    PERMISSION_READ_EXTERNAL_STORAGE);
        }
        return false;
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    pickImage();
                } else { //permission is not granted }
                    return;
                }
            }
        }
    }


    public void getRetrofitCall(){

        if(NetworkUtil.isInternetConnected(getActivity())){
            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
            String achiId = shouldBeUpdated ? achievementID : "";

            OrgReqCreateAchievments orgReqCreateAchiement = OrgReqCreateAchievments.get(pref.getOrgId(),achiId,
                    listOfImages,strDescription,strOthers,strSubTitle,strTitle,strName,action);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrgResCreateAchievments> resCreateAchievement = mPaalanServices.orgCreateAchievements(orgReqCreateAchiement);

            resCreateAchievement.enqueue(new Callback<OrgResCreateAchievments>() {
                @Override
                public void onResponse(Call<OrgResCreateAchievments> call, Response<OrgResCreateAchievments> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();
                            int status = dbAdapter.populatingAchievementsIntoDB(pref.getOrgId(),response.body().getData()[0].getAchievementid(),
                                    achievementID, strName,strTitle, strSubTitle, strDescription, strOthers,
                                        imgDecodableFirst, imgDecodableSecond, imgDecodableThird, imgDecodableForth,"F");
                            String message = status==0 ? getResources().getString(R.string.achievement_created) :
                                    getResources().getString(R.string.achievement_updated);
                            CommanUtils.showToast(getActivity(),message);
                            getActivity().getSupportFragmentManager().popBackStack();

                            dbAdapter.close();
                            getClearFields();

                        } else {
                            CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_went_wrong));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<OrgResCreateAchievments> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }

    private void getClearFields(){

        achievementID= "";
        strTitle = "";
        strSubTitle = "";
        strDescription = "";
        strOthers = "";
        strName = "";
        imgDecodableFirst = "";
        imgDecodableSecond = "";
        imgDecodableThird = "";
        imgDecodableForth = "";

        edtTitle.setText("");
        edtSubTitle.setText("");
        edtDescription.setText("");
        edtOthers.setText("");
        edtName.setText("");

        imgAchievementFirst.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
        imgAchievementSecond.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
        imgAchievementThird.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
        imgAchievementForth.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);

        imgAchievementFirst.setVisibility(View.VISIBLE);
        imgAchievementSecond.setVisibility(View.INVISIBLE);
        imgAchievementThird.setVisibility(View.INVISIBLE);
        imgAchievementForth.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!shouldBeUpdated) {
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getStringArray(R.array.achievements_array)[0],
                    R.drawable.ic_arrow_back_black_24dp);
        }else{
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.update_acheivement),
                    R.drawable.ic_arrow_back_black_24dp);
        }
    }


    private void loadAchievementAttachments(ImageView imgView,String url){
        imgView.setVisibility(View.VISIBLE);
        if(url.contains("http://")) {
            CommanUtils.updateImage(getActivity(),imgView,url,R.drawable.ic_add_circle_outline_black_24dp);
        }else{
            imgView.setImageBitmap(CommanUtils.decodeBase64(url));
        }
    }


    private String getImageData(Cursor cursor,int columnIndex){
        return  CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(
                BitmapFactory.decodeFile(cursor.getString(columnIndex))));
    }

}
