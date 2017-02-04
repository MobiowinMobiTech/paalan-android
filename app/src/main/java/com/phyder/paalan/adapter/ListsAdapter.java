package com.phyder.paalan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.RoundedImageView;
import com.phyder.paalan.utils.TextViewOpenSansRegular;
import com.phyder.paalan.utils.TextViewOpenSansSemiBold;


public class ListsAdapter extends  ArrayAdapter<String> {

    private final static String TAG = ListsAdapter.class.getCanonicalName();
    private String[] titleItemsList,subTitleItemsList,eventDatesORgroupLogo;
    private Context context;
    private int status = 0;


    public ListsAdapter(Context context, int resource, String[] objects1,String[] objects2,String[] objects3) {
        super(context, resource, objects1);
        status=resource;
        titleItemsList = objects1;
        subTitleItemsList = objects2;
        eventDatesORgroupLogo = objects3;
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
            convertView = layoutInflater.inflate(R.layout.fragment_list_row,null);
            viewHolder.txtTitle = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtPrimaryRow);
            viewHolder.txtSubTitle = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtSecondaryRow);
            viewHolder.txtDay = (TextViewOpenSansSemiBold) convertView.findViewById(R.id.txtDayRow);
            viewHolder.txtDate = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtDateRow);
            viewHolder.llDateCircle = (LinearLayout) convertView.findViewById(R.id.rlDateCircle);
            viewHolder.logo = (RoundedImageView) convertView.findViewById(R.id.img_logo);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.txtTitle.setText("" + titleItemsList[position]);
            viewHolder.txtSubTitle.setText("" + subTitleItemsList[position]);

            if(eventDatesORgroupLogo!=null && status==1){

                viewHolder.llDateCircle.setVisibility(View.GONE);
                viewHolder.logo.setVisibility(View.VISIBLE);
                CommanUtils.updateImage(context,viewHolder.logo,eventDatesORgroupLogo[position],R.drawable.unknown);

            }else if(eventDatesORgroupLogo!=null){
                viewHolder.logo.setVisibility(View.GONE);
                viewHolder.llDateCircle.setVisibility(View.VISIBLE);
                try {
                    viewHolder.txtDay.setText("" + eventDatesORgroupLogo[position].substring(0,2));
                    viewHolder.txtDate.setText("" + eventDatesORgroupLogo[position]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                viewHolder.logo.setVisibility(View.GONE);
                viewHolder.llDateCircle.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return convertView;
    }


    class ViewHolder {

        TextViewOpenSansRegular txtTitle,txtSubTitle,txtDate;
        TextViewOpenSansSemiBold txtDay;
        LinearLayout llDateCircle;
        RoundedImageView logo;
    }
}
