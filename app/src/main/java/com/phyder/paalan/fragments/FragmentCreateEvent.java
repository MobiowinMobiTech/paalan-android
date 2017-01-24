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
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FragmentCreateEvent extends Fragment {

    private final static String TAG = FragmentCreateEvent.class.getCanonicalName();

    private Spinner spinnerOsversions;
    private String[] state = { "Select Event Category" ,"Medical Camps", "Social Awareness", "Child care", "Health and Fitness",
            "Food distribution", "Charity", "Misc" };

    private EditTextOpenSansRegular edtTitle, edtSubtitle, edtDescription, edtOther;
    private ButtonOpenSansSemiBold btnCreateEvent;
    private TextViewOpenSansRegular edtStartDate,edtEndDate;
    private ImageView imgStartDate, imgEndDate;

    private String orgId = "", eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate, strCategory= "";
    private DBAdapter dbAdapter;
    private boolean shouldBeUpdated = false;
    private int mYear, mMonth, mDay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_event, null, false);

        edtTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_title);
        edtSubtitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOther = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);
        edtStartDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_startdate);
        edtEndDate = (TextViewOpenSansRegular) view.findViewById(R.id.txt_enddate);
        imgStartDate = (ImageView) view.findViewById(R.id.img_start_date);
        imgEndDate = (ImageView) view.findViewById(R.id.img_enddate_calender);
        btnCreateEvent = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_create_event);

        spinnerOsversions = (Spinner) view.findViewById(R.id.osversions);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOsversions.setAdapter(adapter_state);

        Bundle bundle = getArguments();

        if(bundle!=null) {

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            eventId = bundle.getString("ID");
            edtTitle.setText(bundle.getString("TITLE"));
            edtSubtitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOther.setText(bundle.getString("OTHER"));
            edtStartDate.setText(bundle.getString("STARTDATE"));
            edtEndDate.setText(bundle.getString("ENDDATE"));
            String category = bundle.getString("CATEGORY");
            int pos = 0;
            for(int i=0;i<state.length;i++){
                if(state[i].equals(category)){
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

                if(strTitle.isEmpty()){
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
                }else {
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
            OrgReqCreateEvent reqUpdateProfile = OrgReqCreateEvent.get(orgId, eventId, strTitle, strSubtitle,
                    strDescription, strStartDate, strEndDate, strOther,action);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqUpdateProfile);
            resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
                @Override
                public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
                    Log.d(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter = new DBAdapter(getContext());
                            dbAdapter.open();
                            strCategory = strCategory.equals("Select Event Category") ? "" : strCategory;

                            if (shouldBeUpdated) {
                                dbAdapter.updateEvent(eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate,
                                        strCategory);
                                getActivity().getSupportFragmentManager().popBackStack();
                                Toast.makeText(getActivity(), getResources().getString(R.string.event_updated), Toast.LENGTH_LONG)
                                        .show();
                            } else {
                                dbAdapter.insertEvent(response.body().getData()[0].getEventid(), strTitle, strSubtitle, strDescription, strOther,
                                        strStartDate, strEndDate, "F",strCategory);
                                Toast.makeText(getActivity(), getResources().getString(R.string.event_created), Toast.LENGTH_LONG)
                                        .show();
                            }
                            dbAdapter.close();
                            getClearFields();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                                    .show();
                        }
                    } else if (response.body() == null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }


                @Override
                public void onFailure(Call<OrgResCreateEvent> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
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

        edtTitle.setText("");
        edtSubtitle.setText("");
        edtDescription.setText("");
        edtOther.setText("");
        edtStartDate.setText("");
        edtEndDate.setText("");

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

