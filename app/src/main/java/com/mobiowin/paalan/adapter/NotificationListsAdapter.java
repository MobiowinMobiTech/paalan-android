package com.mobiowin.paalan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobiowin.paalan.utils.TextViewOpenSansRegular;
import com.mobiowin.paalan.utils.TextViewOpenSansSemiBold;
import com.phyder.paalan.R;


public class NotificationListsAdapter extends  ArrayAdapter<String> {

    private final static String TAG = NotificationListsAdapter.class.getSimpleName();

    private String[] title,messages,readed;
    private Context context;


    public NotificationListsAdapter(Context context, int resource, String[] objects1, String[] objects2,
                                    String[] objects3) {
        super(context, resource, objects1);
        title = objects1;
        messages = objects2;
        readed = objects3;
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
            convertView = layoutInflater.inflate(R.layout.fragment_notification_list_row,null);
            viewHolder.txtTitle = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtMessage = (TextViewOpenSansRegular) convertView.findViewById(R.id.txtMessage);
            viewHolder.txtNew = (TextViewOpenSansSemiBold) convertView.findViewById(R.id.txtNew);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.txtTitle.setText(title[position]);
            viewHolder.txtMessage.setText(messages[position]);
            int messageReaded = readed[position].equals("false") ? View.VISIBLE : View.GONE;
            viewHolder.txtNew.setVisibility(messageReaded);
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }


    class ViewHolder {

        TextViewOpenSansRegular txtTitle,txtMessage;
        TextViewOpenSansSemiBold txtNew;
    }
}
