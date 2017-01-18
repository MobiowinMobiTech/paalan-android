package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phyder.paalan.R;

/**
 * Created by yashika on 18/1/17.
 */
public class BasicRegistrationInfo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_basic_info,null);
        return view;
    }
}
