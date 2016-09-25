package com.aboluo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.adapter.MenuGridviewAdapter;
import com.aboluo.adapter.MenuListViewAdapter;
import com.aboluo.com.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MenuFragment extends Fragment {
    private View view;
    private ListView menu_listview;
    private GridView menu_gridview_top,menu_gridview_youlove;
    private List<String> listinfo;
    private List<String>  list_grid_info;
    private MenuListViewAdapter menuListViewAdapter;
    private MenuGridviewAdapter menuGridviewTopAdapter;
    private MenuGridviewAdapter menuGridviewLoveAdapter;
    private  Context context =null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_menu, null);
        } else {

        }
        init();
        for (int i = 0; i < 9; i++) {
            listinfo.add(i,"分类"+i);
            list_grid_info.add(i,"小类"+i);
        }
         menuListViewAdapter = new MenuListViewAdapter(listinfo,MenuFragment.this.getActivity(),1);
        menu_listview.setAdapter(menuListViewAdapter);
        menuGridviewTopAdapter = new MenuGridviewAdapter(list_grid_info,context);
        menu_gridview_top.setAdapter(menuGridviewTopAdapter);
        menuGridviewLoveAdapter = new MenuGridviewAdapter(list_grid_info,context);
        menu_gridview_youlove.setAdapter(menuGridviewLoveAdapter);
        menu_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MenuFragment.this.getActivity(), position+"", Toast.LENGTH_SHORT).show();
                menuListViewAdapter.setSelectedPosition(position);
                menuListViewAdapter.notifyDataSetChanged();
                list_grid_info.clear();
                for (int i = 0; i < (position+10); i++) {
                    list_grid_info.add(i,"小凤类"+i);
                    menuGridviewLoveAdapter.notifyDataSetChanged();
                    menuGridviewTopAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }
    private void init()
    {
       context = MenuFragment.this.getActivity();
        list_grid_info= new ArrayList<>();
        listinfo = new ArrayList<>();
        menu_listview = (ListView) view.findViewById(R.id.menu_listview);
        menu_gridview_top = (GridView) view.findViewById(R.id.menu_gridview_top);
        menu_gridview_youlove = (GridView) view.findViewById(R.id.menu_gridview_youlove);
    }
}
