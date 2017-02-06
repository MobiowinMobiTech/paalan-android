package com.phyder.paalan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.RegisterUser;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dharmedra Gupta
 * date 19/10/2016
 */
public class SlidingImageAdapter extends PagerAdapter {

    private List<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private ButtonOpenSansSemiBold btnRegistration;
    private ImageView imageView;

    public SlidingImageAdapter(Context context, List<String> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        btnRegistration = (ButtonOpenSansSemiBold)imageLayout.findViewById(R.id.btn_register);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RegisterUser.class));
            }
        });
        CommanUtils.updateImage(context, imageView, IMAGES.get(position),R.drawable.paalan_logo);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void getRegistrationVisible_Invisible(int status){

        if(btnRegistration!=null)
            btnRegistration.setVisibility(status);
    }

}
