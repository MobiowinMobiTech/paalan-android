package com.phyder.paalan.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;

public class FragmentConnectWithUs extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connect_with_us, container, false);

        ImageView imgFacebook = (ImageView)view.findViewById(R.id.imgFacebook);
        ImageView imgGooglePlus = (ImageView)view.findViewById(R.id.imgGooglePlus);
        ImageView imgInstagram = (ImageView)view.findViewById(R.id.imgInstagram);
        ImageView imgTwitter = (ImageView)view.findViewById(R.id.imgTwitter);



        imgFacebook.setOnClickListener(this);
        imgGooglePlus.setOnClickListener(this);
        imgInstagram.setOnClickListener(this);
        imgTwitter.setOnClickListener(this);



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getStringArray(R.array.drawer_array)[5]);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (view.getId()){
            case R.id.imgFacebook:
                intent.setData(Uri.parse(getString(R.string.url_facebook)));
                break;
            case R.id.imgGooglePlus:
                intent.setData(Uri.parse(getString(R.string.url_google_plus)));
                break;
            case R.id.imgInstagram:
                intent.setData(Uri.parse(getString(R.string.url_instagram)));
                break;
            case R.id.imgTwitter:
                intent.setData(Uri.parse(getString(R.string.url_twitter)));
                break;
        }
        startActivity(intent);
    }
}
