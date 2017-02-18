package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.HelpCenterListBean;

import java.util.List;

/**
 * Created by CJ on 2017/2/16.
 */

public class HelpCenterListviewAdapter extends BaseAdapter {
    private LayoutInflater mlayoutInflater;
    private Context mcontext;
    private List<HelpCenterListBean.ListBean> mlist;

    public HelpCenterListviewAdapter(Context context, List<HelpCenterListBean.ListBean> list) {
        this.mlayoutInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mlayoutInflater.inflate(R.layout.helpcenter_listview_item, null);
            holder.tv_helpcenter_lv_item = (TextView) convertView.findViewById(R.id.tv_helpcenter_lv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_helpcenter_lv_item.setText(mlist
                .get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView tv_helpcenter_lv_item;
    }
}
