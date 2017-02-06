package com.phyder.paalan.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;

import java.io.ByteArrayOutputStream;

/**
 * Created by Pramod Waghmare on 6/2/17.
 */
public class DonateView extends Fragment {
    private static final String TAG = DonateView.class.getSimpleName();
    Spinner spinnerCategory;
    AutoCompleteTextViewOpenSansRegular txtOtherCategory;
    TextInputLayout categoryHolder;
    String[] categories;
    ImageView imgSelectedCategory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View donateView = inflater.inflate(R.layout.fragment_donate_view,null);
        donateView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        categories = getResources().getStringArray(R.array.donate_category);

        txtOtherCategory = (AutoCompleteTextViewOpenSansRegular)donateView.findViewById(R.id.edtCategory);
        categoryHolder = (TextInputLayout)donateView.findViewById(R.id.viewCategory);

        imgSelectedCategory = (ImageView)donateView.findViewById(R.id.imageCategory);


        spinnerCategory = (Spinner)donateView.findViewById(R.id.spinnerSelectCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categories);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemSelected: "+position+" "+l);
                Log.d(TAG, "onItemSelected: length "+categories.length);
                if (position == categories.length - 1)
                    categoryHolder.setVisibility(View.VISIBLE);
                else
                    categoryHolder.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return donateView;
    }

    public String getSelectedCategory() {
        return categories[spinnerCategory.getSelectedItemPosition()];
    }

    public String getDonateCategoryImage() {
        BitmapDrawable drawable = (BitmapDrawable) imgSelectedCategory.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
