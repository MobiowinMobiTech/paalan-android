package com.phyder.paalan.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.phyder.paalan.R;
import com.phyder.paalan.model.OrgAddressInfo;

/**
 * Created by yashika on 18/1/17.
 */
public class AddressInformation extends Fragment {

    EditText edtAddress, edtCity, edtState, edtCountry, edtPincode;
    private boolean isForDonate;
    private Spinner spinnerDonateOption;
    private String[] categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            isForDonate = getArguments().getBoolean("isForDonate");
        }catch (Exception ex){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_address_info,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));

        edtAddress = (EditText)view.findViewById(R.id.edtOrgAddress);
        edtCity = (EditText)view.findViewById(R.id.edtOrgCity);
        edtCountry = (EditText)view.findViewById(R.id.edtOrgCountry);
        edtPincode = (EditText)view.findViewById(R.id.edtOrgPincode);
        edtState = (EditText)view.findViewById(R.id.edtOrgState);

        if (isForDonate){

            categories = new String[2];
            categories[0] = "Home Pickup";
            categories[1] = "Other";

            spinnerDonateOption = (Spinner)view.findViewById(R.id.spinnerSelectDonateoption);
            spinnerDonateOption.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categories);
            spinnerDonateOption.setAdapter(adapter);

        }

        return view;
    }


    /**
     * Function to set organization info
     */
    public OrgAddressInfo setOrganisationInfo() {
        OrgAddressInfo orgAddressInfo = new OrgAddressInfo();
        orgAddressInfo.setAddress(edtAddress.getText().toString());
        orgAddressInfo.setCity(edtCity.getText().toString());
        orgAddressInfo.setCountry(edtCountry.getText().toString());
        orgAddressInfo.setPincode(edtPincode.getText().toString());
        orgAddressInfo.setState(edtState.getText().toString());

       return orgAddressInfo;

    }

    public String getAddress() {
        return edtAddress.getText().toString() +", "+edtCity.getText().toString()+","+edtState.getText().toString()+
                ", "+edtPincode.getText().toString() +", "+ edtCountry.getText().toString();
    }

    public int getPickUpOption() {
        return spinnerDonateOption.getSelectedItemPosition();
    }
}
