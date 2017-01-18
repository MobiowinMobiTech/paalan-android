package com.phyder.paalan.adapter;

import android.content.Context;
import android.media.Image;
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
import com.phyder.paalan.utils.RoundedImageView;

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
        ViewHolder viewHolder = null;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.ind_dashbord_model, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bind(position);

        return view;
    }

    class ViewHolder {

        private View mView;

        private TextView textView;
        private ImageView imageView;
        private Button btnCreate, btnView;

        ViewHolder(View view) {
            mView = view;

            textView = (TextView) view.findViewById(R.id.item);
            imageView = (ImageView) view.findViewById(R.id.icon);
            btnCreate = (Button) view.findViewById(R.id.btn_Create);
            btnView = (Button) view.findViewById(R.id.btn_view);
//            view.setTag(this);

            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // check view type publish achivement
                    String type = textView.getText().toString();
                    if (type.equals("publish")) {

                    } else if (type.equals("advertise")) {

                    }

                    int pos = (Integer) v.getTag();
                    fragment = pos == 0 ? new FragmentCreateAchievement() : new FragmentContactUs();
                    FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.platform, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });


            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    fragment = pos == 0 ? new FragmentViewAchievement() : new FragmentContactUs();
                    FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.platform, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });
        }

        void bind(int position) {
            mView.setBackgroundColor(colors[position]);

            textView.setText(itemname[position]);
            imageView.setImageResource(imgid[position]);

            btnCreate.setTag(position);
            btnView.setTag(position);
        }
    }
}