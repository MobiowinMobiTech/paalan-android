package com.phyder.paalan.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FragmentCreateEvent extends Fragment {

    private final static String TAG = FragmentCreateEvent.class.getCanonicalName();

    private Spinner spinnerOsversions;
    private EditTextOpenSansRegular edtName,edtTitle, edtSubtitle, edtDescription, edtOther,edtLocation;
    private ButtonOpenSansSemiBold btnCreateEvent;
    private TextViewOpenSansRegular edtStartDate,edtEndDate;
    private ImageView imgStartDate, imgEndDate;

    private String  strName,eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate,strLocation,
            orgId = "",strCategory = "";
    private DBAdapter dbAdapter;
    private PreferenceUtils pref;
    private boolean shouldBeUpdated = false;
    private int mYear, mMonth, mDay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, null, false);

        dbAdapter = new DBAdapter(getContext());
        pref = new PreferenceUtils(getActivity());

        edtName = (EditTextOpenSansRegular) view.findViewById(R.id.edt_name);
        edtTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_title);
        edtSubtitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOther = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);
        edtLocation = (EditTextOpenSansRegular) view.findViewById(R.id.edt_location);
        edtStartDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_startdate);
        edtEndDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_enddate);
        imgStartDate = (ImageView) view.findViewById(R.id.img_start_date);
        imgEndDate = (ImageView) view.findViewById(R.id.img_enddate_calender);
        btnCreateEvent = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_create_event);

        strCategory = getResources().getStringArray(R.array.event_category)[0];

        spinnerOsversions = (Spinner) view.findViewById(R.id.osversions);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.event_category));
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);

        Bundle bundle = getArguments();

        if(bundle!=null) {

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            eventId = bundle.getString("ID");
            edtName.setText(bundle.getString("NAME"));
            edtTitle.setText(bundle.getString("TITLE"));
            edtSubtitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOther.setText(bundle.getString("OTHER"));
            edtStartDate.setText(bundle.getString("STARTDATE"));
            edtEndDate.setText(bundle.getString("ENDDATE"));
            edtLocation.setText(bundle.getString("LOCATION"));
            String category = bundle.getString("CATEGORY");

            int pos = 0;
            for(int i=0;i<getResources().getStringArray(R.array.event_category).length;i++){
                if(getResources().getStringArray(R.array.event_category)[i].equals(category)){
                    pos=i;
                    break;
                }
            }
            spinnerOsversions.setSelection(pos);

        }

        btnCreateEvent.setText(shouldBeUpdated ? getResources().getString(R.string.Update) :
                getResources().getString(R.string.Create));

        spinnerOsversions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCategory = spinnerOsversions.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strTitle = edtTitle.getText().toString();
                strSubtitle = edtSubtitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strStartDate = edtStartDate.getText().toString();
                strOther = edtOther.getText().toString();
                strEndDate = edtEndDate.getText().toString();
                strLocation = edtLocation.getText().toString();
                strName = edtName.getText().toString();

                if(strName.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_name));
                }else if(strTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_title));
                }else if(strSubtitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_sub_title));
                }else if(strDescription.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_description));
                }else if(strStartDate.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_startdate));
                }else if(strEndDate.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_enddate));
                }else if(strOther.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_others));
                }else if(strLocation.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_location));
                }else{
                    getRetrofitCall();
                }

            }
        });

        imgStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtStartDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        edtStartDate.setTextColor(getResources().getColor(R.color.primary_text));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        imgEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtEndDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        edtEndDate.setTextColor(getResources().getColor(R.color.primary_text));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return view;
    }


    public void getRetrofitCall() {

        if (NetworkUtil.isInternetConnected(getActivity())) {
            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
            OrgReqCreateEvent reqCreateEvent = OrgReqCreateEvent.get(orgId, eventId, strName,strTitle, strSubtitle,
                    strDescription, strStartDate, strEndDate, strOther,strCategory,strLocation,action);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqCreateEvent);
            resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
                @Override
                public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter.open();

                            int status = dbAdapter.populatingEventsIntoDB(pref.getOrgId(),
                                    response.body().getData()[0].getEventid()
                                    ,eventId,strName,strTitle, strSubtitle, strDescription, strOther, strStartDate,
                                    strEndDate,"F",strCategory,strLocation,null);
                            String message = status==0 ? getResources().getString(R.string.event_created) :
                                    getResources().getString(R.string.event_updated);
                            CommanUtils.showToast(getActivity(),message);
                            getActivity().getSupportFragmentManager().popBackStack();

                            dbAdapter.close();
                            getClearFields();

                        } else {
                            CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_went_wrong));
                        }
                    } else if (response.body() == null) {
                        CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_server));
                    }
                }


                @Override
                public void onFailure(Call<OrgResCreateEvent> call, Throwable t) {
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_timeout));
                }
            });
        } else {
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }

    private void getClearFields() {

        eventId = "";
        strTitle = "";
        strSubtitle = "";
        strDescription = "";
        strOther = "";
        strStartDate = "";
        strEndDate = "";
        strLocation = "";
        strName = "";

        edtTitle.setText("");
        edtSubtitle.setText("");
        edtDescription.setText("");
        edtOther.setText("");
        edtStartDate.setText("");
        edtEndDate.setText("");
        edtLocation.setText("");
        edtName.setText("");

        spinnerOsversions.setSelection(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!shouldBeUpdated) {
            ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getStringArray(R.array.events_array)[0]);
        }else{
            ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.update_event));
        }

    }

}

