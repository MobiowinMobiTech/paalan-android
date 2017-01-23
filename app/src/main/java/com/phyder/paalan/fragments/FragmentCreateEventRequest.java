package com.phyder.paalan.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import java.util.Calendar;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentCreateEventRequest extends Fragment implements View.OnClickListener {

    private EditText edtTitle, edtSubtitle, edtDescription, edtStartDate, edtOther, edtEndDate;
    private Button btnCreateEvent;
    private int mYear, mMonth, mDay;
    // Button btnStartDate, btnEnddate;
    private ImageView imgstartdate, imgenddate;
    String orgId = "dfgdfg", location = "Malad Mumbai";
    private String eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate;
    private DBAdapter dbAdapter;
    private boolean shouldBeUpdated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_request, container, false);

        edtTitle = (EditText) view.findViewById(R.id.edt_event_title);
        edtSubtitle = (EditText) view.findViewById(R.id.edt_event_subtitle);
        edtDescription = (EditText) view.findViewById(R.id.edt_event_discripn);
        edtStartDate = (EditText) view.findViewById(R.id.edt_event_startdate);
        edtOther = (EditText) view.findViewById(R.id.edt_event_other);
        edtEndDate = (EditText) view.findViewById(R.id.edt_event_enddate);
        imgstartdate = (ImageView) view.findViewById(R.id.img_start_date);
        imgenddate = (ImageView) view.findViewById(R.id.img_enddate_calender);
        btnCreateEvent = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_create_event);
        eventId = "svxcvv";



        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strTitle = edtTitle.getText().toString();
                strSubtitle = edtSubtitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strStartDate = edtStartDate.getText().toString();
                strOther = edtOther.getText().toString();
                strEndDate =edtEndDate.getText().toString();

                getRetrofitCall();

            }
        });

        imgstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get Current Date

                final Calendar c = Calendar.getInstance();
                //final Calendar c = Calendar.getInstance();
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


        imgenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    // Get Current Date
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


        // txtStartDate.setOnClickListener(this);
        final String strEndDate = edtEndDate.getText().toString();
        if (NetworkUtil.isInternetConnected(getActivity())) {
            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
            OrgReqCreateEvent reqUpdateProfile = OrgReqCreateEvent.get(orgId, eventId, strTitle, strSubtitle, strDescription, strStartDate, strEndDate, location, strOther);

            //   Device.newInstance(getActivity());

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqUpdateProfile);
            resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
                @Override
                public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
                    // Log.d(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter = new DBAdapter(getContext());
                            dbAdapter.open();
                            dbAdapter.updateEventTimeSpan(response.body().getMessage());
                            if (shouldBeUpdated) {
                                dbAdapter.updateEvent(eventId, strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate);
                                getActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                dbAdapter.insertEvent(response.body().getData()[0].getEventid(), strTitle, strSubtitle, strDescription, strOther, strStartDate, strEndDate, "F");
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
//                            Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        } else {
            CommanUtils.getInternetAlert(getActivity());
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

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!shouldBeUpdated) {
            ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(), getResources().getString(R.string.create_event));
            ;
        } else {
            ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(), getResources().getString(R.string.update_event));
        }

    }


}

