package com.phyder.paalan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phyder.paalan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class ListsAdapter extends  ArrayAdapter<String> {

    private ArrayList<String> itemsList;
    private Context context;

    public ListsAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        itemsList= (ArrayList<String>) objects;
        this.context=context;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_achievement_row,null);
            viewHolder.txtAchievements = (TextView) convertView.findViewById(R.id.txtRow);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtAchievements.setText(""+itemsList.get(position));

        return convertView;
    }


    class ViewHolder {

        TextView txtAchievements;
    }
}
