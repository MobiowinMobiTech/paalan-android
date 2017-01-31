package com.phyder.paalan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.phyder.paalan.R;
import com.phyder.paalan.services.RegisterUserInterface;
import com.phyder.paalan.utils.OrgAddressInfo;

/**
 * Created by yashika on 18/1/17.
 */
public class AddressRegistrationInfo extends Fragment implements View.OnClickListener {

    EditText edtAddress, edtCity, edtState, edtCountry, edtPincode;
    RegisterUserInterface registerUserInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_address_info,null);


        edtAddress = (EditText)view.findViewById(R.id.edtOrgAddress);
        edtCity = (EditText)view.findViewById(R.id.edtOrgCity);
        edtCountry = (EditText)view.findViewById(R.id.edtOrgCountry);
        edtPincode = (EditText)view.findViewById(R.id.edtOrgPincode);
        edtState = (EditText)view.findViewById(R.id.edtOrgState);


        Button btnPrevious = (Button)view.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);

        Button btnRegister = (Button)view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerUserInterface = (RegisterUserInterface)context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPrevious:
                registerUserInterface.navigatePrevious();
                break;
            case R.id.btnRegister:
                setOrganisationInfo();
                break;
        }
    }

    /**
     * Function to set organization info
     */
    private void setOrganisationInfo() {
        OrgAddressInfo orgAddressInfo = new OrgAddressInfo();
        orgAddressInfo.setAddress(edtAddress.getText().toString());
        orgAddressInfo.setCity(edtCity.getText().toString());
        orgAddressInfo.setCountry(edtCountry.getText().toString());
        orgAddressInfo.setPincode(edtPincode.getText().toString());
        orgAddressInfo.setState(edtState.getText().toString());

        registerUserInterface.setAddressRegInfo(orgAddressInfo);
        registerUserInterface.registerUser();

    }
}
