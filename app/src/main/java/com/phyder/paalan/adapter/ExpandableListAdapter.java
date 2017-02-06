package com.phyder.paalan.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.util.HashMap;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = ExpandableListAdapter.class.getSimpleName();
    private Context _context;
        private String[] _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, String[]> _listDataChild;

        public ExpandableListAdapter(Context context, String[] listDataHeader,
                                     HashMap<String, String[]> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {

            return this._listDataChild.get(this._listDataHeader[groupPosition])
                    [childPosititon];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items, null);
            }

            TextViewOpenSansRegular txtListChild = (TextViewOpenSansRegular) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);

            Log.d(TAG, "getGroupView: group child name "+childText);


            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {


            return this._listDataChild.get(this._listDataHeader[groupPosition])
                    .length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextViewOpenSansRegular lblListHeader = (TextViewOpenSansRegular) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setText(headerTitle);


            Resources resources = _context.getResources();
            final int resourceId = resources.getIdentifier(headerTitle.toLowerCase().replace(" ",""), "drawable",
                    _context.getPackageName());
            try {
                Log.d(TAG, "getGroupView: group name "+headerTitle.toLowerCase().replace(" ",""));
                lblListHeader.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(resourceId),null,null,null);
            }catch (Exception ex){

            }


            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
