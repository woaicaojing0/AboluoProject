package com.aboluo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.MenuGridviewAdapter;
import com.aboluo.adapter.MenuListViewAdapter;
import com.aboluo.com.GoodsListActivity;
import com.aboluo.com.R;
import com.aboluo.model.GoodsBigType;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class MenuFragment extends Fragment {
    private View view;
    private ListView menu_listview;    //左边大类的listview
    private GridView menu_gridview_top, menu_gridview_youlove;   //右边上边的gridiview 和右边下边的猜你喜欢
    private MenuListViewAdapter menuListViewAdapter;   //listview 适配器
    private MenuGridviewAdapter menuGridviewTopAdapter;
    private MenuGridviewAdapter menuGridviewLoveAdapter;
    private Context context = null;
    private RequestQueue requestQueue;  //volley调度
    private StringRequest requestByGoodsType1; //访问大类的request
    private GoodsBigType.ResultBean resultBean; //保存大类的信息
    private GoodsBigType.ResultBean resultBean2; // 保存小类的信息
    private String url;
   private SweetAlertDialog sweetAlertDialog;
private String APPToken = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_menu, null);
        } else {

        }
        init();
        GetTypeList();

        menu_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sweetAlertDialog.show();
                Toast.makeText(MenuFragment.this.getActivity(), position + "", Toast.LENGTH_SHORT).show();
                final int type_id = resultBean.getGoodsTypeList().get(position).getGoodsTypeId();
                menuListViewAdapter.setSelectedPosition(position);
                menuListViewAdapter.notifyDataSetChanged();
                StringRequest requestByGoodsType3 = new StringRequest(Request.Method.POST, url + "/api/GoodsType/ReceiveGoodsTypeSubList", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace("\\", "");//去掉'/'
                        response = response.substring(1, response.length() - 1); //去掉头尾引号。
                        Log.i("woaicaojing", response);
                        Gson gson = new Gson();
                        GoodsBigType goodsBigType = gson.fromJson(response, GoodsBigType.class);
                        if (goodsBigType.isIsSuccess()) {
                            resultBean2 = goodsBigType.getResult();
                            menuGridviewTopAdapter.setMlistinfo(resultBean2);
                            menuGridviewTopAdapter.notifyDataSetChanged();
                        } else {
                        }
                        sweetAlertDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("APPToken",APPToken);
                        map.put("GoodsTypeId", String.valueOf(type_id));
                        return map;
                    }
                };
                requestQueue.add(requestByGoodsType3);

            }
        });
        menu_gridview_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int goods_type_id =  resultBean2.getGoodsTypeList().get(position).getGoodsTypeId();
               String goods_type_name =  resultBean2.getGoodsTypeList().get(position).getGoodsTypeName();
                Intent intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("goods_type_id",goods_type_id);
                intent.putExtra("goods_type_name",goods_type_name);
                startActivity(intent);
            }
        });
        return view;
    }
    //获取初始化数据
    private void GetTypeList() {
        sweetAlertDialog.show();
        requestByGoodsType1 = new StringRequest(Request.Method.POST, url + "/api/GoodsType/ReceiveGoodsTypeList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");//去掉'/'
                response = response.substring(1, response.length() - 1); //去掉头尾引号。
                Log.i("woaicaojing", response);
                Gson gson = new Gson();
                GoodsBigType goodsBigType = gson.fromJson(response, GoodsBigType.class);
                if (goodsBigType.isIsSuccess()) {
                    resultBean = goodsBigType.getResult();
                    menuListViewAdapter = new MenuListViewAdapter(resultBean, MenuFragment.this.getActivity(), 0);
                    menu_listview.setAdapter(menuListViewAdapter);
                    StringRequest requestByGoodsType2 = new StringRequest(Request.Method.POST, url + "/api/GoodsType/ReceiveGoodsTypeSubList", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replace("\\", "");//去掉'/'
                            response = response.substring(1, response.length() - 1); //去掉头尾引号。
                            Log.i("woaicaojing", response);
                            Gson gson = new Gson();
                            GoodsBigType goodsBigType = gson.fromJson(response, GoodsBigType.class);
                            if (goodsBigType.isIsSuccess()) {
                                resultBean2 = goodsBigType.getResult();
                                menuGridviewTopAdapter = new MenuGridviewAdapter(resultBean2, context);
                                menu_gridview_top.setAdapter(menuGridviewTopAdapter);
                                sweetAlertDialog.dismiss();
                            } else {
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("APPToken", APPToken);
                            map.put("GoodsTypeId", String.valueOf(resultBean.getGoodsTypeList().get(0).getGoodsTypeId()));
                            return map;
                        }
                    };
                    requestQueue.add(requestByGoodsType2);
                } else {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("woaicaojing", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(requestByGoodsType1);
    }

    private void init() {
        context = MenuFragment.this.getActivity();
        menu_listview = (ListView) view.findViewById(R.id.menu_listview);
        menu_gridview_top = (GridView) view.findViewById(R.id.menu_gridview_top);
        menu_gridview_youlove = (GridView) view.findViewById(R.id.menu_gridview_youlove);
        requestQueue = MyApplication.getRequestQueue();
        url = CommonUtils.GetValueByKey(context, "apiurl");
        resultBean = new GoodsBigType.ResultBean();
        resultBean2 = new GoodsBigType.ResultBean();
        sweetAlertDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("加载中");
        APPToken=CommonUtils.GetValueByKey(context,"APPToken");
    }
}
