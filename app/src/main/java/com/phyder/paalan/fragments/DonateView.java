package com.phyder.paalan.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.phyder.paalan.utils.CommanUtils;

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
    Bitmap photo = null;


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
                Log.d(TAG, "onItemSelected: length "+ categories[position]);
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
            photo = (Bitmap) data.getExtras().get("data");
            imgSelectedCategory.setImageBitmap(photo);
            saveProfilePicture(photo);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Bitmap profilePic = CommanUtils.getUserProfile(getActivity(), "donate");
        Log.d(TAG, "getProfileUpdate: img "+profilePic);
        if (profilePic != null)
            imgSelectedCategory.setImageBitmap(profilePic);
    }

    /**
     * Function to save profile picture in Sp
     * @param bitmap
     */
    private void saveProfilePicture(Bitmap bitmap) {
        Log.d(TAG, "XXsaveProfilePicture: frag ");
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        CommanUtils.saveProfilePic(getActivity(),"donate" ,temp);
    }

    private void updateImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    public String getSelectedCategory() {
        Log.d(TAG, "validateData: "+category);
        return category.equalsIgnoreCase("Others") ? txtOtherCategory.getText().toString() : category;
    }

    public String getDonateCategoryImage() {
        if (photo != null)
            return CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(photo));
        else
            return "";
    }
}
