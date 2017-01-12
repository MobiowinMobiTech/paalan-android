package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phyder.paalan.R;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentCreateAchievement extends Fragment{





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_event_history,null,false);
            init(view);
        return view;
    }

    private void init(View view) {
    }
}
