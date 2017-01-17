package com.phyder.paalan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phyder.paalan.R;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] itemname;
    private final Integer[] imgid;
    private int colors[];

    public CustomListAdapter(Context context, int resource, String[] objects, Integer[] image) {
        super(context, resource, objects);
        this.context = context;
        this.itemname = objects;
        this.imgid = image;
        colors = context.getResources().getIntArray(R.array.colors);
    }


    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ind_dashbord_model, null);
            viewHolder.textView = (TextView) view.findViewById(R.id.item);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        view.setBackgroundColor(colors[position]);

        viewHolder.textView.setText(itemname[position]);
        viewHolder.imageView.setImageResource(imgid[position]);
        return view;

    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}