package com.mobiowin.paalan.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobiowin.paalan.utils.TextViewOpenSansSemiBold;
import com.phyder.paalan.R;
import com.mobiowin.paalan.model.WhatsNewScreenModel;
import com.squareup.picasso.Picasso;

/**
 * Created by yashika on 31/1/17.
 */
public class WhatsNewSlide extends Fragment {
    WhatsNewScreenModel whatsNewScreenModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from calling activity
        whatsNewScreenModel = new WhatsNewScreenModel();

        Bundle bundle = getArguments();
        whatsNewScreenModel.setTitle(bundle.getString("title"));
        whatsNewScreenModel.setMessage(bundle.getString("message"));
        whatsNewScreenModel.setImageLink(bundle.getString("imageLink"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whats_new,null);
        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        TextViewOpenSansSemiBold txtTitle = (TextViewOpenSansSemiBold)view.findViewById(R.id.txtScreenTitle);
        TextViewOpenSansSemiBold txtMessage = (TextViewOpenSansSemiBold)view.findViewById(R.id.txtScreenDescription);

        txtTitle.setText(whatsNewScreenModel.getTitle());
        txtMessage.setText(whatsNewScreenModel.getMessage());

        ImageView imageScreen = (ImageView)view.findViewById(R.id.imageScreen);

        Picasso.with(getActivity())
                .load(whatsNewScreenModel.getImageLink())
                .placeholder(R.drawable.paalan_logo)
                .error(R.drawable.paalan_logo)
                .into(imageScreen);


        return view;
    }

}
