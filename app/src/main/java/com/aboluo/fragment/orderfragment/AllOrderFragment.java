package com.aboluo.fragment.orderfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.AllOrderAdapter;
import com.aboluo.com.R;
import com.aboluo.model.SearchOrderBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cj34920 on 2016/9/30.
 */

public class AllOrderFragment extends Fragment implements View.OnClickListener {
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String APPToken;
    private String ImageURL;
    private String URL;
    private View view;
    private PullToRefreshListView allorder_listview;
    private SearchOrderBean orderBean;
    private Gson gson;
    private AllOrderAdapter allOrderAdapter;
    private String MemberId;
    private int InitPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder, null);
        MemberId = CommonUtils.GetMemberId(AllOrderFragment.this.getContext());
        init();
        allorder_listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                //这里写下拉刷新的任务
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullUpToRefresh");
                //这里写上拉加载更多的任务
                InitPage++;
                GetInfo(InitPage);
            }
        });
        return view;
    }

    private void init() {
        allorder_listview = (PullToRefreshListView) view.findViewById(R.id.allorder_listview);
        requestQueue = MyApplication.getRequestQueue();
        APPToken = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(), "APPToken");
        URL = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(), "apiurl");
        ImageURL = CommonUtils.GetValueByKey(AllOrderFragment.this.getActivity(), "ImgUrl");
        gson = new Gson();
        GetInfo(1);
    }

    private void GetInfo(final int page) {
        stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/GetOrderListByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicaojingallorder", response);
                if (orderBean == null) {
                    orderBean = new SearchOrderBean();
                    orderBean = gson.fromJson(response, SearchOrderBean.class);
                    allOrderAdapter = new AllOrderAdapter(AllOrderFragment.this.getContext(), orderBean.getResult());
                    allorder_listview.setAdapter(allOrderAdapter);
                } else {
                    SearchOrderBean searchOrderBean = new SearchOrderBean();
                    searchOrderBean = gson.fromJson(response, SearchOrderBean.class);
                    orderBean.getResult().addAll(searchOrderBean.getResult());
                    allOrderAdapter.notifyDataSetChanged();
                }

                allOrderAdapter.setCancelOrderOnclickListener(AllOrderFragment.this);
                allOrderAdapter.setFindGoodsOnclickListener(AllOrderFragment.this);
                allOrderAdapter.setPayOnclickListener(AllOrderFragment.this);
                allOrderAdapter.setSureOkOnclickListener(AllOrderFragment.this);
                allOrderAdapter.setItemOnclickListener(AllOrderFragment.this);
                allorder_listview.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] htmlBodyBytes = error.networkResponse.data;
                String result = new String(htmlBodyBytes);
                Log.i("woaicaojingallorder", result);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OrderStatus", "0");
                map.put("CurrentPage", String.valueOf(page));
                map.put("PageSize", "3");
                map.put("NCount", "5");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (key) {
            //btn_findgoods, btn_ok, btn_cancelorder, btn_payorder;
            case R.id.content_view: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Toast.makeText(AllOrderFragment.this.getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
            case R.id.btn_findgoods: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Toast.makeText(AllOrderFragment.this.getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
            case R.id.btn_ok: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Toast.makeText(AllOrderFragment.this.getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
            case R.id.btn_cancelorder: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Toast.makeText(AllOrderFragment.this.getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
            case R.id.btn_payorder: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Toast.makeText(AllOrderFragment.this.getContext(), position + "", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
