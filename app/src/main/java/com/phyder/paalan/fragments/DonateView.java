package com.phyder.paalan.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;
import com.phyder.paalan.utils.CommanUtils;


import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Pramod Waghmare on 6/2/17.
 */
public class DonateView extends Fragment {
    private static final String TAG = DonateView.class.getSimpleName();
    private static final int REQUEST_CODE = 99;
    Spinner spinnerCategory;
    AutoCompleteTextViewOpenSansRegular txtOtherCategory;
    EditText edtDate;
    TextInputLayout categoryHolder;
    String[] categories;
    ImageView imgSelectedCategory;
    Bitmap photo = null;
    private int mYear, mMonth, mDay;
    Calendar calendar;
    private String[] pickUpCategories;
    private Spinner spinnerDonateOption;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View donateView = inflater.inflate(R.layout.fragment_donate_view,null);
        categories = getResources().getStringArray(R.array.donate_category);


        calendar  = Calendar.getInstance();

        txtOtherCategory = (AutoCompleteTextViewOpenSansRegular)donateView.findViewById(R.id.edtCategory);
        edtDate = (EditText)donateView.findViewById(R.id.edtDate);
        categoryHolder = (TextInputLayout)donateView.findViewById(R.id.viewCategory);

        // image of donate item
        imgSelectedCategory = (ImageView)donateView.findViewById(R.id.imageCategory);

        edtDate.setFocusable(false);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        // donate category
        spinnerCategory = (Spinner)donateView.findViewById(R.id.spinnerSelectCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categories);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == categories.length - 1)
                    categoryHolder.setVisibility(View.VISIBLE);
                else {
                    categoryHolder.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgSelectedCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImage();
            }
        });

        pickUpCategories = new String[2];
        pickUpCategories[0] = "Home Pickup";
        pickUpCategories[1] = "Other";

        spinnerDonateOption = (Spinner)donateView.findViewById(R.id.spinnerSelectDonateoption);
        spinnerDonateOption.setVisibility(View.VISIBLE);
        ArrayAdapter<String> pickUpAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,pickUpCategories);
        spinnerDonateOption.setAdapter(pickUpAdapter);
        return donateView;
    }

    /**
     * Function to select date of donate
     */
    private void selectDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edtDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                edtDate.setTextColor(getResources().getColor(R.color.primary_text));

                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.YEAR,year);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            photo = (Bitmap) data.getExtras().get("data");
            imgSelectedCategory.setImageBitmap(photo);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (photo != null)
            imgSelectedCategory.setImageBitmap(photo);
    }

    /**
     * Function to update image of donate product
     */
    private void updateImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * Function to get selected categort of donate type
     * @return
     */
    public String getSelectedCategory() {
        if (spinnerCategory.getSelectedItemPosition() == 0 )
            return "";
        else if (spinnerCategory.getSelectedItemPosition() == categories.length - 1)
            return txtOtherCategory.getText().toString();
        else
        return categories[spinnerCategory.getSelectedItemPosition()];
    }

    /**
     * Get image of donated product
     * @return : image of product
     */
    public String getDonateCategoryImage() {
        if (photo != null) {
            Log.d(TAG, "getDonateCategoryImage: image if ");
            return CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(photo));
        }
        else {
            Log.d(TAG, "getDonateCategoryImage: image else ");
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getSelectedDate() {
        return calendar.getTimeInMillis()+"";
    }


    public int getPickUpOption() {
        return spinnerDonateOption.getSelectedItemPosition();
    }

}
