package com.phyder.paalan.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by Pramod Waghmare on 6/2/17.
 */
public class DonateView extends Fragment {
    private static final String TAG = DonateView.class.getSimpleName();
    private static final int REQUEST_CODE = 99;
    Spinner spinnerCategory;
    AutoCompleteTextViewOpenSansRegular txtOtherCategory;
    TextInputLayout categoryHolder;
    String[] categories;
    ImageView imgSelectedCategory;
    String category = "";


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
        spinnerCategory.setSelected(true);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "onItemSelected: "+position+" "+l);
                Log.d(TAG, "onItemSelected: length "+categories.length);
                if (position == categories.length - 1)
                    categoryHolder.setVisibility(View.VISIBLE);
                else {
                    categoryHolder.setVisibility(View.GONE);
                    category = categories[position];
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



        return donateView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgSelectedCategory.setImageBitmap(photo);
        }
    }

    private void updateImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    public String getSelectedCategory() {
        if (category.equalsIgnoreCase(categories[categories.length]))
            return txtOtherCategory.getText().toString();
        else
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
