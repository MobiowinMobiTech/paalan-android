package com.phyder.paalan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.TextViewOpenSansRegular;


public class ListsAdapter extends  ArrayAdapter<String> {

    private final static String TAG = ListsAdapter.class.getCanonicalName();
    private String[] titleItemsList,subTitleItemsList;
    private Context context;


    public ListsAdapter(Context context, int resource, String[] objects1,String[] objects2) {
        super(context, resource, objects1);
        titleItemsList = objects1;
        subTitleItemsList = objects2;
        this.context = context;
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
            viewHolder.txtTitle = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtPrimaryRow);
            viewHolder.txtSubTitle = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtSecondaryRow);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.txtTitle.setText("" + titleItemsList[position]);
            viewHolder.txtSubTitle.setText("" + subTitleItemsList[position]);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
         return convertView;
    }


    class ViewHolder {

        TextViewOpenSansRegular txtTitle,txtSubTitle;
    }
}
