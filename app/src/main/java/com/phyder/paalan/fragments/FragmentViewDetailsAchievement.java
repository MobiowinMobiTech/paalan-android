package com.phyder.paalan.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.payload.request.RequestLogin;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteAchievement;
import com.phyder.paalan.payload.response.ResponseLogin;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 18/1/17.
 */

public class FragmentViewDetailsAchievement extends Fragment {

    private final static String TAG = FragmentViewDetailsAchievement.class.getCanonicalName();

    private LinearLayout llAttachmentblock;
    private TextViewOpenSansRegular txtTitle,txtSubTitle,txtDesc,txtOther;
    private ImageView imgFirst,imgSecond,imgThird,imgForth;
    private ButtonOpenSansSemiBold btnUpdate,btnDelete;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private String achievementID,strTitle,strSubTitle,strDescriptions,strOthers,
            strFirstImage,strSecondImage,strThirdImage,strForthImage;

    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_details_achievement,container,false);
        init(view);
        getPoulatedData();
        clickEventFire();
        return view;
    }


    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        llAttachmentblock = (LinearLayout) view.findViewById(R.id.llAttachmentSection);

        txtTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtTitleValue);
        txtSubTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtSubTitleValue);
        txtDesc = (TextViewOpenSansRegular) view.findViewById(R.id.txtDescValue);
        txtOther = (TextViewOpenSansRegular) view.findViewById(R.id.txtOtherValue);

        imgFirst = (ImageView) view.findViewById(R.id.imgFirstView);
        imgSecond = (ImageView) view.findViewById(R.id.imgSecondView);
        imgThird = (ImageView) view.findViewById(R.id.imgThirdView);
        imgForth = (ImageView) view.findViewById(R.id.imgForthView);

        btnUpdate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnUpdateAchievement);
        btnDelete = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDeleteAchievement);

    }



    private void getPoulatedData() {

        if(PaalanGetterSetter.getAchievementID()!=null) {
            achievementID = PaalanGetterSetter.getAchievementID();
        }

        dbAdapter.open();
        cursor = dbAdapter.getAchievementById(achievementID);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    strTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE));
                    strSubTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SUB_TITLE));
                    strDescriptions = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_DESCRIPTION));
                    strOthers = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_OTHERS));

                    strFirstImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE));
                    strSecondImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE));
                    strThirdImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE));
                    strForthImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE));

                }while (cursor.moveToNext());
            }
        }
        dbAdapter.close();

        if(strFirstImage.isEmpty() || strFirstImage.contains("http://localhost")) {
            llAttachmentblock.setVisibility(View.GONE);
        }else{

            imgFirst.setVisibility(View.VISIBLE);
            imgFirst.setImageBitmap(CommanUtils.decodeBase64(strFirstImage));

            if(!strSecondImage.isEmpty() || strSecondImage.contains("http://localhost")){
                imgSecond.setVisibility(View.VISIBLE);
                imgSecond.setImageBitmap(CommanUtils.decodeBase64(strSecondImage));
            }

            if(!strThirdImage.isEmpty() || strThirdImage.contains("http://localhost")){
                imgThird.setVisibility(View.VISIBLE);
                imgThird.setImageBitmap(CommanUtils.decodeBase64(strThirdImage));
            }

            if(!strForthImage.isEmpty() || strForthImage.contains("http://localhost")){
                imgForth.setVisibility(View.VISIBLE);
                imgForth.setImageBitmap(CommanUtils.decodeBase64(strForthImage));
            }
        }

        txtTitle.setText(strTitle);
        txtSubTitle.setText(strSubTitle);
        txtDesc.setText(strDescriptions);
        txtOther.setText(strOthers);

    }




    private void clickEventFire() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("OPERATION_STATUS",true);
                bundle.putString("ID",achievementID);
                bundle.putString("TITLE",strTitle);
                bundle.putString("SUB_TITLE",strSubTitle);
                bundle.putString("DESCRIPTION",strDescriptions);
                bundle.putString("OTHER",strOthers);
                bundle.putString("IMAGE1",strFirstImage);
                bundle.putString("IMAGE2",strSecondImage);
                bundle.putString("IMAGE3",strThirdImage);
                bundle.putString("IMAGE4",strForthImage);
                Fragment fragment = new FragmentCreateAchievement();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                        addToBackStack(null).commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRetrofitCall();
            }
        });
    }




    public void getRetrofitCall() {

        if(NetworkUtil.isInternetConnected(getActivity())) {

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            OrgReqDeleteAchievement reqDeleteAchievement = OrgReqDeleteAchievement.get(pref.getOrgId(), achievementID);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<OrgResDeleteAchievement> resLogin = mPaalanServices.orgDeleteAchievements(reqDeleteAchievement);

            resLogin.enqueue(new Callback<OrgResDeleteAchievement>() {
                @Override
                public void onResponse(Call<OrgResDeleteAchievement> call, Response<OrgResDeleteAchievement> response) {
                    Log.e(TAG, "onResponse: " + response.body());

                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter.open();
                            dbAdapter.deleteAchievement(achievementID);
                            dbAdapter.close();
                            Toast.makeText(getActivity(), getResources().getString(R.string.achievement_deleted), Toast.LENGTH_LONG)
                                    .show();
                            getActivity().getSupportFragmentManager().popBackStack();

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrgResDeleteAchievement> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        }else{
            CommanUtils.getInternetAlert(getActivity());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.update_delete_acheivement));
        getPoulatedData();
    }
}
