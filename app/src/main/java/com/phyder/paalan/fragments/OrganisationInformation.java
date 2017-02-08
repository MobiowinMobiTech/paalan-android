package com.phyder.paalan.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.services.Device;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yashika on 18/1/17.
 */
public class OrganisationInformation extends Fragment {

    private EditText edtName, edtEmail, edtContactNo, edtPassword, edtConfirmPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_orgnisation_info,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        edtName = (EditText)view.findViewById(R.id.edtName);
        edtEmail = (EditText)view.findViewById(R.id.edtEmail);
        edtContactNo = (EditText)view.findViewById(R.id.edtContactNumber);
        edtPassword = (EditText)view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)view.findViewById(R.id.edtConfirmPassword);


        return view;
    }

    /**
     * Function to check validation on password
     * @return : password validation result
     */
    public boolean isValidPassword() {
        return  edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())
                && edtPassword.getText().length() > 6;
    }

    /**
     * Function to check validation on email
     * @return : email validation result
     */
    public boolean isValidEmailId(String email){
        Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(email);
        Log.d("", "isBasicInfoFilled: valid email i/p:"+email);
        if (!matcher1.matches()) {
            //show your message if not matches with email pattern
            return false;
        }else
            return true;
    }


    /**
     * Function to save individual info
     * @return : individual info
     */
    public IndivitualReqRegistration setIndividualInfo(){

        IndivitualReqRegistration indivitualReqRegistration = IndivitualReqRegistration.get(
                edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtContactNo.getText().toString(),
                edtPassword.getText().toString(),
                Device.getDeviceId(getActivity()),"","");

        return indivitualReqRegistration;

    }

}
