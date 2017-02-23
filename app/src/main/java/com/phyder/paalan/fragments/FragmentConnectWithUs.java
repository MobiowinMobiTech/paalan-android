package com.phyder.paalan.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.helper.DialogPopupListener;
import com.phyder.paalan.payload.request.SubmitFeedback;
import com.phyder.paalan.payload.response.SubmitDonateResponse;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;
import com.phyder.paalan.utils.ButtonOpenSansRegular;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Class used to display connect with paalan
 * @author Pramod Waghmare
 */
public class FragmentConnectWithUs extends Fragment implements View.OnClickListener {
    private AutoCompleteTextViewOpenSansRegular edtEmail, edtName, edtMobile, edtMessage;
    private DialogPopupListener dialogPopupListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect_with_us, container, false);

        ImageView imgFacebook = (ImageView)view.findViewById(R.id.imgFacebook);
        ImageView imgGooglePlus = (ImageView)view.findViewById(R.id.imgGooglePlus);
        ImageView imgInstagram = (ImageView)view.findViewById(R.id.imgInstagram);
        ImageView imgTwitter = (ImageView)view.findViewById(R.id.imgTwitter);

        edtEmail = (AutoCompleteTextViewOpenSansRegular)view.findViewById(R.id.edtEmail);
        edtName = (AutoCompleteTextViewOpenSansRegular)view.findViewById(R.id.edtName);
        edtMobile = (AutoCompleteTextViewOpenSansRegular)view.findViewById(R.id.edtMobile);
        edtMessage = (AutoCompleteTextViewOpenSansRegular)view.findViewById(R.id.edtMessage);

        ButtonOpenSansSemiBold btnSubmit = (ButtonOpenSansSemiBold)view.findViewById(R.id.btnSubmitFeedback);

        imgFacebook.setOnClickListener(this);
        imgGooglePlus.setOnClickListener(this);
        imgInstagram.setOnClickListener(this);
        imgTwitter.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getStringArray(R.array.drawer_array)[5],
                R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (view.getId()){
            case R.id.imgFacebook:
                intent.setData(Uri.parse(getString(R.string.url_facebook)));
                startActivity(intent);
                break;
            case R.id.imgGooglePlus:
                intent.setData(Uri.parse(getString(R.string.url_google_plus)));
                startActivity(intent);
                break;
            case R.id.imgInstagram:
                intent.setData(Uri.parse(getString(R.string.url_instagram)));
                startActivity(intent);
                break;
            case R.id.imgTwitter:
                intent.setData(Uri.parse(getString(R.string.url_twitter)));
                startActivity(intent);
                break;
            case R.id.btnSubmitFeedback:
                submitFeedback();
                break;
        }

    }

    /**
     * Function to submit feedback
     */
    private void submitFeedback() {
        if (isValidData()){
            submitFeedbackWithServer();
        }
    }

    /**
     * Function to submit feedback data  to server
     */
    private void submitFeedbackWithServer() {
        Log.d("", "submitFeedbackWithServer: success with valid");

        CommanUtils.showDialog(getActivity());

        SubmitFeedback submitFeedback = new SubmitFeedback();
        submitFeedback.setAction(Social.SUBMIT_ACTION);
        submitFeedback.setType(Social.CONNECT);
        submitFeedback.setEntity(Social.IND_ENTITY);
        SubmitFeedback.Data data = new SubmitFeedback.Data();
        data.setMobileno(edtMobile.getText().toString());
        data.setEmailid(edtEmail.getText().toString());
        data.setName(edtName.getText().toString());
        data.setMessage(edtMessage.getText().toString());
        submitFeedback.setData(data);


        // server call for submit feedback

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Call<SubmitDonateResponse> resRegistrationCall = mPaalanServices.submitFeedback(submitFeedback);
        resRegistrationCall.enqueue(new Callback<SubmitDonateResponse>() {
            @Override
            public void onResponse(Call<SubmitDonateResponse> call, Response<SubmitDonateResponse> response) {
                CommanUtils.hideDialog();
                if (response.body().getStatus().equalsIgnoreCase("success")){
                    CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),
                            getString(R.string.feedback_submit_success));
                    dialogPopupListener.onCancelClicked("");
                }else {
                    CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),
                            getString(R.string.technical_issue));
                }
            }

            @Override
            public void onFailure(Call<SubmitDonateResponse> call, Throwable t) {
                Log.d("", "submitFeedbackWithServer onResponse: "+call.toString());
                CommanUtils.hideDialog();
                CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),
                        getString(R.string.technical_issue));
            }
        });



    }

    /**
     * Function to validate data
     * @return : validation result
     */
    private boolean isValidData() {
        String email = edtEmail.getText().toString();
        String name = edtName.getText().toString();
        String mobile = edtMobile.getText().toString();
        String message = edtMessage.getText().toString();

        if (TextUtils.isEmpty(name)){
            CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),getString(R.string.error_empty_name));
            return false;
        }else if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
            CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),getString(R.string.mobile_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(email) || !CommanUtils.isEmailValid(email)){
            CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),getString(R.string.email_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(message)){
            CommanUtils.showAlert(getActivity(),getString(R.string.feedback_title),getString(R.string.feedback_message_validation_message));
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dialogPopupListener = (DialogPopupListener)context;
    }
}
