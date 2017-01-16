package com.phyder.paalan.adapter;

import android.app.Activity;
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
        final boolean[] selectedItem = new boolean[itemname.length];
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


//        if (position == 0) {
//            rowView.setBackgroundColor(Color.parseColor("RED"));
//        } else if (position == 1) {
//            rowView.setBackgroundColor(Color.parseColor("YELLOW"));
//        }

        //TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);


//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//				android.R.layout.simple_list_item_1, list1) {
//			@Override
//			public View getView(int position, View convertView, ViewGroup parent) {
//				View view = super.getView(position, convertView, parent);
//				if (selectedItem[position]) {
//					view.setBackgroundColor(Color.parseColor("#66F44336"));
//				} else {
//					view.setBackgroundColor(Color.parseColor("#EEEEEE"));
//				}
//				return view;
//			}
//		};
        //view.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.GREY);


        viewHolder.textView.setText(itemname[position]);
        viewHolder.imageView.setImageResource(imgid[position]);
        //extratxt.setText("Description "+itemname[position]);
        return view;

    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}