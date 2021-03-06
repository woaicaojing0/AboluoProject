package com.aboluo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aboluo.com.R;
import com.aboluo.model.GoodsBigType;

import java.util.List;

/**
 * Created by CJ on 2016/9/25.
 */

public class MenuListViewAdapter extends BaseAdapter {
    private GoodsBigType.ResultBean  mlistinfo;
    private Context mcontext;
    private int mSelectedPostion;

    public void setSelectedPosition(int selectedPostion) {
        this.mSelectedPostion = selectedPostion;
    }

    public MenuListViewAdapter(GoodsBigType.ResultBean listinfo, Context Context,int selectedPostion) {
        this.mcontext = Context;
        this.mlistinfo = listinfo;
        this.mSelectedPostion = selectedPostion;
    }

    @Override
    public int getCount() {
        return mlistinfo.getGoodsTypeList().size();
    }

    @Override
    public Object getItem(int position) {
        return mlistinfo.getGoodsTypeList().get(position);
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
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.menu_listview_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.menu_listview_item_text);
            holder.menu_listview_item_bigId = (TextView) convertView.findViewById(R.id.menu_listview_item_bigId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position == mSelectedPostion)
        {
            holder.textView.setBackgroundColor(mcontext.getResources().getColor( R.color.white));
        }else {
            holder.textView.setBackgroundColor(mcontext.getResources().getColor( R.color.main_bottom));
        }
        holder.textView.setText(mlistinfo.getGoodsTypeList().get(position).getGoodsTypeName());
        holder.menu_listview_item_bigId.setText(mlistinfo.getGoodsTypeList().get(position).getGoodsTypeId()+"");
        return convertView;
    }

    class ViewHolder {
        public TextView textView,menu_listview_item_bigId;
    }
}
