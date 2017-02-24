package com.mobiowin.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobiowin.paalan.db.DBAdapter;
import com.mobiowin.paalan.helper.OrganizerProfileListener;
import com.mobiowin.paalan.helper.PaalanGetterSetter;
import com.mobiowin.paalan.payload.response.ResponseOrganizerProfile;
import com.mobiowin.paalan.utils.NetworkUtil;
import com.mobiowin.paalan.utils.PreferenceUtils;
import com.mobiowin.paalan.utils.TextViewOpenSansRegular;
import com.phyder.paalan.R;
import com.mobiowin.paalan.activity.ActivityFragmentPlatform;
import com.mobiowin.paalan.db.Attributes;
import com.mobiowin.paalan.payload.request.OrganizationReqDeleteAchievement;
import com.mobiowin.paalan.payload.response.OrgResDeleteAchievement;
import com.mobiowin.paalan.services.Device;
import com.mobiowin.paalan.services.PaalanServices;
import com.mobiowin.paalan.helper.Social;
import com.mobiowin.paalan.utils.ButtonOpenSansSemiBold;
import com.mobiowin.paalan.utils.CommanUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 18/1/17.
 */

public class FragmentViewDetailsAchievement extends Fragment  {

    private final static String TAG = FragmentViewDetailsAchievement.class.getCanonicalName();

    private LinearLayout llAttachmentblock,llAchiever;
    private TextViewOpenSansRegular txtName,txtTitle,txtSubTitle,txtDesc,txtRemarks;
    private ImageView imgFirst,imgSecond,imgThird,imgForth;
    private ButtonOpenSansSemiBold btnUpdate,btnDelete,btnViewProfile;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private String achievementID,orgId,strName,strTitle,strSubTitle,strDescriptions,strRemarks,
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
        llAchiever = (LinearLayout) view.findViewById(R.id.llAchiever);

        txtName = (TextViewOpenSansRegular) view.findViewById(R.id.txtNameValue);
        txtTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtTitleValue);
        txtSubTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtSubTitleValue);
        txtDesc = (TextViewOpenSansRegular) view.findViewById(R.id.txtDescValue);
        txtRemarks = (TextViewOpenSansRegular) view.findViewById(R.id.txtRemarkValue);

        imgFirst = (ImageView) view.findViewById(R.id.imgFirstView);
        imgSecond = (ImageView) view.findViewById(R.id.imgSecondView);
        imgThird = (ImageView) view.findViewById(R.id.imgThirdView);
        imgForth = (ImageView) view.findViewById(R.id.imgForthView);

        btnViewProfile = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnView);
        btnUpdate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnUpdateAchievement);
        btnDelete = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDeleteAchievement);

        if(pref.getLoginType().equals(Social.IND_ENTITY)){
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }else{
            llAchiever.setVisibility(View.GONE);
        }
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
                    orgId = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_ORG_ID));
                    strName = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_NAME));
                    strTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE));
                    strSubTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SUB_TITLE));
                    strDescriptions = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_DESCRIPTION));
                    strRemarks = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_REMARKS));

                    strFirstImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE));
                    strSecondImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE));
                    strThirdImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE));
                    strForthImage = cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE));

                }while (cursor.moveToNext());
            }
        }
        dbAdapter.close();

        if(strFirstImage.isEmpty()){
            llAttachmentblock.setVisibility(View.GONE);
        }else {
            llAttachmentblock.setVisibility(View.VISIBLE);

            if (!strFirstImage.isEmpty())
                loadAchievementAttachments(imgFirst, strFirstImage);

            if (!strSecondImage.isEmpty())
                loadAchievementAttachments(imgSecond, strSecondImage);

            if (!strThirdImage.isEmpty())
                loadAchievementAttachments(imgThird, strThirdImage);

            if (!strForthImage.isEmpty())
                loadAchievementAttachments(imgForth, strForthImage);
        }

        txtName.setText(strName);
        txtTitle.setText(strTitle);
        txtSubTitle.setText(strSubTitle);
        txtDesc.setText(strDescriptions);
        txtRemarks.setText(strRemarks);

    }




    private void clickEventFire() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("OPERATION_STATUS",true);
                bundle.putString("ID",achievementID);
                bundle.putString("NAME",strName);
                bundle.putString("TITLE",strTitle);
                bundle.putString("SUB_TITLE",strSubTitle);
                bundle.putString("DESCRIPTION",strDescriptions);
                bundle.putString("REMARKS",strRemarks);
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


        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.getOrganizerProfile(getActivity(), orgId, new OrganizerProfileListener() {
                    @Override
                    public void onSuccess(Response<ResponseOrganizerProfile> responseOrganizerProfile) {

                        Bundle bundle=new Bundle();
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_DP_IMG,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getDpImgLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_NAME,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getName());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_MOBILE_NO,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getMobileNo());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_EMAIL,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getEmailId());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_ADDRESS,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getAddress());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_ROLE,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getRole());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_IS_NEWS_LETTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getIsNewsLetter());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_IS_GOVT_REGISTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getIsGovtRegister());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_REGISTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getRegistrationNo());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_FB_LINK,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getFbLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_LINKEDIN,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getLinkedinLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_WEBSITE,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getWebsiteLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_TWITTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getTwitterLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_PRESENCE_AREA,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getPresenceArea());
                        Fragment fragment =new FragmentGroupsProfile();
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                                addToBackStack(null).commit();
                    }
                });
            }
        });
    }




    public void getRetrofitCall() {

        if(NetworkUtil.isInternetConnected(getActivity())) {

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            OrganizationReqDeleteAchievement reqDeleteAchievement = OrganizationReqDeleteAchievement.get(pref.getOrgId(), achievementID);

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
                            CommanUtils.showToast(getActivity(), getResources().getString(R.string.achievement_deleted));
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_went_wrong));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<OrgResDeleteAchievement> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(pref.getLoginType().equals(Social.IND_ENTITY)){
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.acheivement_details),
                    R.drawable.ic_arrow_back_black_24dp);
        }else {
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.update_delete_acheivement),
                    R.drawable.ic_arrow_back_black_24dp);
        }
        getPoulatedData();
    }

    private void loadAchievementAttachments(ImageView imgView,String url){
        imgView.setVisibility(View.VISIBLE);
        if(url.contains("http://")) {
            CommanUtils.updateImage(getActivity(),imgView,url,R.drawable.ic_add_circle_outline_black_24dp);
        }else{
            imgView.setImageBitmap(CommanUtils.decodeBase64(url));
        }
    }


}
