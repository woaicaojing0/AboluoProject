package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aboluo.com.R;

import java.util.List;
import java.util.Map;

/**
 * Created by cj34920 on 2016/11/17.
 */

public class OrderExpendListViewAdapter extends BaseExpandableListAdapter {

    private Map<String, List<String>> mdataset;
    private Context mcontext;
    private String [] mparentList;
    public OrderExpendListViewAdapter(Map<String, List<String>> map, Context context,String [] parentList ) {
        this.mdataset = map;
        this.mcontext = context;
        this.mparentList = parentList;
    }

    @Override
    public int getGroupCount() {
        return mdataset.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mdataset.get(mparentList[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mdataset.get(mparentList[groupPosition]);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mdataset.get(mparentList[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(R.layout.make_order_parent_item, null);
            TextView text = (TextView) convertView.findViewById(R.id.parent_title);
            text.setText("123");

        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
            convertView = layoutInflater.inflate(R.layout.make_order_child_item, null);
            TextView text = (TextView) convertView.findViewById(R.id.child_title);
            text.setText("1243");
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
