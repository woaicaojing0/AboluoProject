package com.aboluo.fragment.orderfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.AllOrderAdapter;
import com.aboluo.com.OrderDetailActivity;
import com.aboluo.com.R;
import com.aboluo.model.BaseModel;
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

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by cj34920 on 2016/9/30.
 */

public class NoreceiveFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Context context;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private String APPToken;
    private String ImageURL;
    private String URL;
    private PullToRefreshListView allorder_listview;
    private SearchOrderBean orderBean;
    private Gson gson;
    private AllOrderAdapter allOrderAdapter;
    private String MemberId;
    private int InitPage = 1;
    private LinearLayout allorder_empty;
    private SweetAlertDialog finishDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder, null);
        init();
        allorder_listview.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                Log.e("TAG", "onPullDownToRefresh");
                //这里写下拉刷新的任务
                InitPage = 1;
                orderBean = null;
                GetInfo(1);
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
        context = NoreceiveFragment.this.getContext();
        MemberId = CommonUtils.GetMemberId(context);
        allorder_listview = (PullToRefreshListView) view.findViewById(R.id.allorder_listview);
        allorder_empty = (LinearLayout) view.findViewById(R.id.allorder_empty);
        requestQueue = MyApplication.getRequestQueue();
        APPToken = CommonUtils.GetValueByKey(context, "APPToken");
        URL = CommonUtils.GetValueByKey(context, "apiurl");
        ImageURL = CommonUtils.GetValueByKey(context, "ImgUrl");
        gson = new Gson();
        //GetInfo(1);
    }

    private void GetInfo(final int page) {
        stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveOrderListByMemberId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean IsEmpty = false;
                allorder_empty.setVisibility(View.GONE);
                allorder_listview.setVisibility(View.VISIBLE);
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicaojingallorder", response);
                SearchOrderBean searchOrderBean = new SearchOrderBean();
                searchOrderBean = gson.fromJson(response, SearchOrderBean.class);
                if (searchOrderBean.isIsSuccess()) {
                    if (orderBean == null) {
                        if (searchOrderBean.getResult().size() == 0) {
                            allorder_empty.setVisibility(View.VISIBLE);
                            allorder_listview.setVisibility(View.GONE);
                            IsEmpty = true;
                        } else {
                            orderBean = new SearchOrderBean();
                            orderBean = gson.fromJson(response, SearchOrderBean.class);
                            allOrderAdapter = new AllOrderAdapter(context, orderBean.getResult());
                            allorder_listview.setAdapter(allOrderAdapter);
                        }
                    } else {
                        if (searchOrderBean.getResult().size() == 0) {
                            Toast.makeText(context,
                                    "没有数据啦", Toast.LENGTH_SHORT).show();
                        } else {
                            orderBean.getResult().addAll(searchOrderBean.getResult());
                            allOrderAdapter.notifyDataSetChanged();
                        }
                    }
                    if (IsEmpty) {
                    } else {
                        allOrderAdapter.setFindGoodsOnclickListener(NoreceiveFragment.this);
                        allOrderAdapter.setSureOkOnclickListener(NoreceiveFragment.this);
                        allOrderAdapter.setItemOnclickListener(NoreceiveFragment.this);
                        allorder_listview.onRefreshComplete();
                    }
                } else {
                    Toast.makeText(context,
                            searchOrderBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoreceiveFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OrderStatus", "30");
                map.put("CurrentPage", String.valueOf(page));
                map.put("PageSize", "10");
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
                    Intent intent = new Intent(NoreceiveFragment.this.getActivity(), OrderDetailActivity.class);
                    intent.putExtra("orderid", orderBean.getResult().get(position).getOrderId());
                    startActivity(intent);
                }
                break;
            case R.id.txt_findgoods: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
//                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
//                    final int position = (Integer) tag;
//                    Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(NoreceiveFragment.this.getActivity(), ExpressDetailActivity.class);
//                    intent.putExtra("OrderId", orderBean.getResult().get(0).getOrderId());
//                    intent.putExtra("ExpressId", orderBean.getResult().get(0).getExpressId());
//                    intent.putExtra("GoodsLogoUrl", orderBean.getResult().get(0).getOrderItemList().get(0).getGoodsLogoUrl().toString());
//                    startActivity(intent);
//                    startActivity(intent);
//                }
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    Intent intent = new Intent(NoreceiveFragment.this.getActivity(), OrderDetailActivity.class);
                    intent.putExtra("orderid", orderBean.getResult().get(position).getOrderId());
                    startActivity(intent);
                }
                break;
            case R.id.txt_ok: //点击添加数量按钮，执行相应的处理
                // 获取 Adapter 中设置的 Tag
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                    final int position = (Integer) tag;
                    //Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                    finishDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("您确定收货")
                            .setCancelText("稍等片刻")
                            .setConfirmText("确认收货")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    finishDialog.dismiss();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finishDialog.dismiss();
                                    ConfirmOrder(String.valueOf(orderBean.getResult().get(position).getOrderId()));
                                }
                            });
                    finishDialog.setCancelable(false);
                    finishDialog.show();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        InitPage = 1;
        orderBean = null;
        GetInfo(1);
    }

    /**
     * 确认收货
     */
    private void ConfirmOrder(final String OrderId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/Order/ReceiveConfirmReceipt", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                Toast.makeText(context, baseModel.getMessage().toString(), Toast.LENGTH_SHORT).show();
                InitPage = 1;
                orderBean = null;
                GetInfo(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("OrderId", OrderId);
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }
}
