package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

/**
 * Created by Pramod Waghmare on 6/2/17.
 */
public class DonateView extends Fragment {
    Spinner spinnerCategory;
    TextViewOpenSansRegular txtOtherCategory;
    String[] categories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View donateView = inflater.inflate(R.layout.fragment_donate_view,null);

        categories = getResources().getStringArray(R.array.donate_category);

        txtOtherCategory = (TextViewOpenSansRegular)donateView.findViewById(R.id.edtCategory);

        spinnerCategory = (Spinner)donateView.findViewById(R.id.spinnerSelectCategory);
        spinnerCategory.setPrompt("Select category");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categories);
        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == categories.length)
                    txtOtherCategory.setVisibility(View.VISIBLE);
                else
                    txtOtherCategory.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return donateView;
    }
}
