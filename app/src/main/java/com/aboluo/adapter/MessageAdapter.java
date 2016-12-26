package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.MessageBean;

import java.util.List;

/**
 * Created by CJ on 2016/12/26.
 */

public class MessageAdapter extends BaseAdapter {
    private List<MessageBean> mlist;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public MessageAdapter(List<MessageBean> list, Context context) {
        this.mcontext = context;
        this.mlist = list;
        layoutInflater = LayoutInflater.from(context);
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
            convertView = layoutInflater.inflate(R.layout.message_listview_item, null);
            holder.message_listview_txt = (TextView) convertView.findViewById(R.id.message_listview_txt);
            holder.message_listview_time = (TextView) convertView.findViewById(R.id.message_listview_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.message_listview_time.setText(mlist.get(position).getCreatetime().toString());
        holder.message_listview_txt.setText(mlist.get(position).getMessageinfo().toString());
        return convertView;
    }

    class ViewHolder {
        private TextView message_listview_txt, message_listview_time;
    }
}
