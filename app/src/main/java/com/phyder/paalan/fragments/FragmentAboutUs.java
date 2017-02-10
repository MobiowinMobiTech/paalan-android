package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;


/**
 * Class used to display Paalan info
 * @author Pramod Waghmare
 */
public class FragmentAboutUs extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);

        TextView txtAboutUs = (TextView)view.findViewById(R.id.txtAboutUs);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txtAboutUs.setText(Html.fromHtml(getString(R.string.about_us_text),Html.FROM_HTML_MODE_LEGACY));
        } else {
            txtAboutUs.setText(Html.fromHtml(getString(R.string.about_us_text)));
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.about_us),
                R.drawable.ic_arrow_back_black_24dp);
    }

}
