package com.phyder.paalan.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.fragments.FragmentContactUs;
import com.phyder.paalan.fragments.FragmentCreateAchievement;
import com.phyder.paalan.fragments.FragmentViewAchievement;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final FragmentActivity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private int colors[];
    private Fragment fragment;

    public CustomListAdapter(FragmentActivity context, int resource, String[] objects, Integer[] image) {
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
            viewHolder.btnCreate = (Button) view.findViewById(R.id.btnCreate);
            viewHolder.btnView = (Button) view.findViewById(R.id.btnView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        view.setBackgroundColor(colors[position]);

        viewHolder.textView.setText(itemname[position]);
        viewHolder.imageView.setImageResource(imgid[position]);

        viewHolder.btnCreate.setTag(position);
        viewHolder.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(Integer)v.getTag();
                fragment = pos==0 ? new FragmentCreateAchievement() : new FragmentContactUs();
                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.platform, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        viewHolder.btnView.setTag(position);
        viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(Integer)v.getTag();
                fragment = pos==0 ? new FragmentViewAchievement() : new FragmentContactUs();
                FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.platform, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        return view;

    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        Button btnCreate,btnView;
    }
}