package com.phyder.paalan.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phyder.paalan.R;

import java.util.Calendar;

/**
 * Created by yashika on 31/1/17.
 */
public class WhatsNewSlide extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_whats_new,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        TextView textView = (TextView)view.findViewById(R.id.txtWhatsNew);
        textView.setText("Time is: "+ Calendar.getInstance().getTimeInMillis());
        return view;
    }

}
