package com.phyder.paalan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.social.DashBoardModel;
import com.phyder.paalan.utils.ItemClickListener;
import com.phyder.paalan.utils.RoundedImageView;

import java.util.ArrayList;

public class HorizontalListVAdapter extends RecyclerView.Adapter<HorizontalListVAdapter.ViewHolder> {

    ArrayList<DashBoardModel> dashBoardModel;
    Context context;

    public HorizontalListVAdapter(Context context, ArrayList<DashBoardModel> dashBoardModels) {
        super();
        this.context = context;
        this.dashBoardModel = dashBoardModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvSpecies.setText(dashBoardModel.get(i).getEvenName());
        viewHolder.imgThumbnail.setImageResource(dashBoardModel.get(i).getEventIcons());

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + dashBoardModel.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "#" + position + " - " + dashBoardModel.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashBoardModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public RoundedImageView imgThumbnail;
        public TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (RoundedImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }

    }



}

