package com.aboluo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.ExpressDetailBean.ResultBean.ListBean;

import java.util.List;

/**
 * Created by CJ on 2016/12/5.
 */

public class ExpressDetailAdapter extends BaseAdapter {
    private List<ListBean> mlist;
    private Context mcontext;
    private LayoutInflater inflater;

    public ExpressDetailAdapter(List<ListBean> list, Context context) {
        this.mlist = list;
        this.mcontext = context;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.activity_express_detail_item, null);
            holder = new ViewHolder();
            holder.express_item_top = (View) convertView.findViewById(R.id.express_item_top);
            holder.express_item_bottom = (View) convertView.findViewById(R.id.express_item_bottom);
            holder.express_item_imge = (ImageView) convertView.findViewById(R.id.express_item_imge);
            holder.express_item_time = (TextView) convertView.findViewById(R.id.express_item_time);
            holder.express_item_status = (TextView) convertView.findViewById(R.id.express_item_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.express_item_top.setVisibility(View.INVISIBLE);
            holder.express_item_imge.setImageResource(R.drawable.circlebig);
            holder.express_item_time.setTextColor(Color.parseColor("#25ae5f"));
            holder.express_item_status.setTextColor(Color.parseColor("#25ae5f"));
        } else {
        }
        holder.express_item_time.setText(mlist.get(position).getTime().toString());
        holder.express_item_status.setText(mlist.get(position).getStatus());
        return convertView;
    }

    class ViewHolder {
        public View express_item_top, express_item_bottom;
        public ImageView express_item_imge;
        public TextView express_item_time, express_item_status;
    }
}
