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
import com.phyder.paalan.activity.ActivityCreateEvent;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentPublishEventRequest extends Fragment implements View.OnClickListener {

    EditText edtTitle, edtSubtitle, edtDescription, edtStartDate, edtOther, edtEndDate;
    Button btnCreateEvent;
    private int mYear, mMonth, mDay;
    // Button btnStartDate, btnEnddate;
    ImageView imgstartdate, imgenddate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_request, container, false);

        edtTitle = (EditText) view.findViewById(R.id.edt_event_title);
        edtSubtitle = (EditText) view.findViewById(R.id.edt_event_subtitle);
        edtDescription = (EditText) view.findViewById(R.id.edt_event_discripn);
        edtStartDate = (EditText) view.findViewById(R.id.edt_event_startdate);
        edtOther = (EditText) view.findViewById(R.id.edt_event_other);
        edtEndDate = (EditText) view.findViewById(R.id.edt_event_enddate);
        imgstartdate = (ImageView) view.findViewById(R.id.img_start_date);
        imgenddate = (ImageView) view.findViewById(R.id.img_enddate_calender);

//        btnStartDate = (Button) view.findViewById(R.id.btn_start_date);
//        btnEnddate = (Button) view.findViewById(R.id.btn_end_date);
        //  btnDatePicker=(Button)view.findViewById(R.id.btn_start_date);
        //  txtDate=(EditText)view.findViewById(R.id.edt_event_startdate);
        btnCreateEvent = (Button) view.findViewById(R.id.btn_create_event);


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "xvmklxcv", Toast.LENGTH_SHORT).show();
                String orgId = "dfgfdg";
                String eventId = "svxcvv";
                String location = "Malad Mumbai";
                String strTitle = edtTitle.getText().toString();
                String strSubtitle = edtSubtitle.getText().toString();
                String strDescription = edtDescription.getText().toString();
                String strStartDate = edtStartDate.getText().toString();
                String strOther = edtOther.getText().toString();

                // txtStartDate.setOnClickListener(this);
                String strEndDate = edtEndDate.getText().toString();
//                Log.d("Title", "" + strTitle + "//Stored Value//" + edtTitle.getText().toString());
//                Log.d("Title", "" + strSubtitle + "//Stored Value//" + edtSubtitle.getText().toString());
//                Log.d("Title", "" + strDescription + "//Stored Value//" + edtDescription.getText().toString());
//                Log.d("Title", "" + strStartDate + "//Stored Value//" + edtStartDate.getText().toString());
//                Log.d("Title", "" + strOther + "//Stored Value//" + edtOther.getText().toString());
//                Log.d("Title", "" + strEndDate + "//Stored Value//" + edtEndDate.getText().toString());


                OrgReqCreateEvent reqUpdateProfile = OrgReqCreateEvent.get(orgId, eventId, strTitle, strSubtitle, strDescription, strStartDate, strEndDate, location, strOther);

                Device.newInstance(getActivity());

                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqUpdateProfile);
                resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
                    @Override
                    public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
                        // Log.d(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<OrgResCreateEvent> call, Throwable t) {

                    }
                });
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


//

        imgenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//                    // Get Current Date
//
                final Calendar c = Calendar.getInstance();
                //final Calendar c = Calendar.getInstance();
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

    @Override
    public void onClick(View v) {

    }
}

