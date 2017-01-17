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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

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
    private EditText edtTitle,edtSubTitle,edtDescription,edtOthers;
    private ImageView imgAchievementFirst,imgAchievementSecond,imgAchievementThird,imgAchievementForth;
    private Button btnSubmit;

    final static int PICK_IMAGE_FROM_GALARY = 1;

    private String imgDecodableFirst = "",imgDecodableSecond = "",imgDecodableThird = "",imgDecodableForth = "";
    private String strTitle = "",strSubTitle = "",strDescription = "",strOthers = "";
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
        edtTitle = (EditText) view.findViewById(R.id.edt_title);
        edtSubTitle = (EditText) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditText) view.findViewById(R.id.edt_description);
        edtOthers = (EditText) view.findViewById(R.id.edt_other);

        imgAchievementFirst = (ImageView) view.findViewById(R.id.imgFirst);
        imgAchievementSecond = (ImageView) view.findViewById(R.id.imgSecond);
        imgAchievementThird = (ImageView) view.findViewById(R.id.imgThird);
        imgAchievementForth = (ImageView) view.findViewById(R.id.imgForth);

        btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        listOfImages =new ArrayList<String>();

        Bundle bundle = getArguments();

        if(bundle!=null){

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            edtTitle.setText(bundle.getString("TITLE"));
            edtSubTitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOthers.setText(bundle.getString("OTHER"));

            imgDecodableFirst=bundle.getString("IMAGE1");
            imgDecodableSecond=bundle.getString("IMAGE2");
            imgDecodableThird=bundle.getString("IMAGE3");
            imgDecodableForth=bundle.getString("IMAGE4");

            if(imgDecodableFirst != null && !imgDecodableFirst.isEmpty()){
                imgAchievementFirst.setImageBitmap(CommanUtils.decodeBase64(imgDecodableFirst));
                imgAchievementSecond.setVisibility(View.VISIBLE);
            }
            if(imgDecodableSecond != null && !imgDecodableSecond.isEmpty()){
                imgAchievementSecond.setImageBitmap(CommanUtils.decodeBase64(imgDecodableSecond));
                imgAchievementThird.setVisibility(View.VISIBLE);
            }
            if(imgDecodableThird != null && !imgDecodableThird.isEmpty()){
                imgAchievementThird.setImageBitmap(CommanUtils.decodeBase64(imgDecodableThird));
                imgAchievementForth.setVisibility(View.VISIBLE);
            }
            if(imgDecodableForth != null && !imgDecodableForth.isEmpty()){
                imgAchievementForth.setImageBitmap(CommanUtils.decodeBase64(imgDecodableForth));
            }

        }
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

                strTitle = edtTitle.getText().toString();
                strSubTitle = edtSubTitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strOthers = edtOthers.getText().toString();

                if(strTitle.isEmpty()){
                    edtTitle.setError(getResources().getString(R.string.error_empty_title));
                } else if(strSubTitle.isEmpty()){
                    edtSubTitle.setError(getResources().getString(R.string.error_empty_sub_title));
                } else if(strDescription.isEmpty()){
                    edtDescription.setError(getResources().getString(R.string.error_empty_description));
                } else if(strOthers.isEmpty()){
                    edtOthers.setError(getResources().getString(R.string.error_empty_others));
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
                    imgAchievementFirst.setImageBitmap(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)));
                    imgDecodableFirst = CommanUtils.encodeToBase64(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)),Bitmap.CompressFormat.JPEG, 100);
                    imgAchievementSecond.setVisibility(View.VISIBLE);
                } else if(imgAchievementStatus == 2){
                    imgAchievementSecond.setImageBitmap(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)));
                    imgDecodableSecond = CommanUtils.encodeToBase64(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)),Bitmap.CompressFormat.JPEG, 100);
                    imgAchievementThird.setVisibility(View.VISIBLE);
                } else if(imgAchievementStatus == 3){
                    imgAchievementThird.setImageBitmap(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)));
                    imgDecodableThird = CommanUtils.encodeToBase64(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)),Bitmap.CompressFormat.JPEG, 100);
                    imgAchievementForth.setVisibility(View.VISIBLE);
                } else if(imgAchievementStatus == 4){
                    imgAchievementForth.setImageBitmap(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)));
                    imgDecodableForth = CommanUtils.encodeToBase64(BitmapFactory
                            .decodeFile(cursor.getString(columnIndex)),Bitmap.CompressFormat.JPEG, 100);
                }
                cursor.close();

            }
        } catch (Exception e) {
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
        CommanUtils.showDialog(getActivity());
        Device.newInstance(getActivity());
        String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
        OrgReqCreateAchievments orgReqCreateAchiement = OrgReqCreateAchievments.get(pref.getOrgId(),listOfImages,strDescription,strOthers,
                strSubTitle,strTitle,action);

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Call<OrgResCreateAchievments> resCreateAchievement = mPaalanServices.orgCreateAchievements(orgReqCreateAchiement);

        resCreateAchievement.enqueue(new Callback<OrgResCreateAchievments>() {
            @Override
            public void onResponse(Call<OrgResCreateAchievments> call, Response<OrgResCreateAchievments> response) {
                Log.e(TAG, "onResponse: " + response.body());
                CommanUtils.hideDialog();
                if (response.isSuccessful()) {

                    if (!response.body().getStatus().equals("error")) {
                        dbAdapter.open();

                        if(shouldBeUpdated){
                            dbAdapter.updateAchievement(response.body().getData()[0].getAchievementid(), strTitle, strSubTitle, strDescription, strOthers,
                                    imgDecodableFirst, imgDecodableSecond, imgDecodableThird, imgDecodableForth);
                            dbAdapter.close();
                        }else {
                            dbAdapter.insertAchievement(response.body().getData()[0].getAchievementid(), strTitle, strSubTitle, strDescription, strOthers,
                                    imgDecodableFirst, imgDecodableSecond, imgDecodableThird, imgDecodableForth,"F");
                            dbAdapter.close();
                        }
                        getClearFields();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrgResCreateAchievments> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommanUtils.hideDialog();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void getClearFields(){

        strTitle = "";
        strSubTitle = "";
        strDescription = "";
        strOthers = "";
        imgDecodableFirst = "";
        imgDecodableSecond = "";
        imgDecodableThird = "";
        imgDecodableForth = "";

        edtTitle.setText("");
        edtSubTitle.setText("");
        edtDescription.setText("");
        edtOthers.setText("");

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
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(),getResources().getStringArray(R.array.achievements_array)[0]);
    }

}
