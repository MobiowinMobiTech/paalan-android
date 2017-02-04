package com.phyder.paalan.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.fragments.FragmentGroupsProfile;
import com.phyder.paalan.fragments.FragmentViewDetailsAchievement;
import com.phyder.paalan.fragments.FragmentViewDetailsEvent;
import com.phyder.paalan.fragments.FragmentViewDetailsRequest;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.RoundedImageView;

import java.util.ArrayList;

public class HorizontalListVAdapter extends RecyclerView.Adapter<HorizontalListVAdapter.ViewHolder> {

    private ArrayList<String> titlesItems,idsItems,logosItems;
    private int image;
    private FragmentActivity context;
    private String redirectPosition;

    public HorizontalListVAdapter(FragmentActivity context, ArrayList<String> logosItems,ArrayList<String> titlesItems,
                                  ArrayList<String> idsItems,int image,String redirectPosition) {
        super();
        this.context = context;
        this.titlesItems = titlesItems;
        this.idsItems = idsItems;
        this.logosItems = logosItems;
        this.image=image;
        this.redirectPosition = redirectPosition;
   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.tvSpecies.setText(titlesItems.get(i));

        if(logosItems==null ) {
            viewHolder.imgThumbnail.setImageResource(image);
        }else if(redirectPosition.equals(Social.NAVIGATE_TO_EVENT)){
            CommanUtils.updateImage(context,viewHolder.imgThumbnail,logosItems.get(i),R.drawable.ic_add_alert_black_24dp);
        }else if(redirectPosition.equals(Social.NAVIGATE_TO_GROUP)){
            CommanUtils.updateImage(context,viewHolder.imgThumbnail,logosItems.get(i),R.drawable.unknown);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigate(redirectPosition,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titlesItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public RoundedImageView imgThumbnail;
        public TextView tvSpecies;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (RoundedImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }

    }

    private void getNavigate(String redirectPage,int pos){

        switch (redirectPage){

            case Social.NAVIGATE_TO_EVENT:
                PaalanGetterSetter.setEventID(idsItems.get(pos));
                context.getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform, new FragmentViewDetailsEvent()).addToBackStack(null).commit();
                break;

            case Social.NAVIGATE_TO_ACHIEVEMENT:
                PaalanGetterSetter.setAchivementID(idsItems.get(pos));
                context.getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform, new FragmentViewDetailsAchievement()).addToBackStack(null).commit();
                break;

            case Social.NAVIGATE_TO_SOCIAL:
                PaalanGetterSetter.setRequestID(idsItems.get(pos));
                context.getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform, new FragmentViewDetailsRequest()).addToBackStack(null).commit();
                break;

            case Social.NAVIGATE_TO_GROUP:
                PaalanGetterSetter.setOrgID(idsItems.get(pos));
                context.getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform, new FragmentGroupsProfile()).addToBackStack(null).commit();
                break;

        }
    }
}

