package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.MessageAdapter;
import com.aboluo.model.RecMsgBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 接收极光推送的消息
 * Created by CJ on 2016/12/26.
 */

public class MessageActivity extends Activity {

    private PullToRefreshListView message_listview;
    private MessageAdapter messageAdapter;
    private ImageView my_info_text_back;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private int currentpages = 1;
    private RecMsgBean list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message_listview = (PullToRefreshListView) findViewById(R.id.message_listview);
        my_info_text_back = (ImageView) findViewById(R.id.my_info_text_back);
        my_info_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        message_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpages = 1;
                initData(currentpages);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentpages++;
                initData(currentpages);
            }
        });
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl2");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        initData(1);
    }

    private void initData(final int currentpages) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL +
                "/api/PushApi/ReceivePushMessage", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                list = gson.fromJson(response, RecMsgBean.class);
                if (list.getListResult() == null || list.getListResult().size() == 0) {
                    Toast.makeText(MessageActivity.this, "暂无推送消息", Toast.LENGTH_SHORT).show();
                } else {
                    messageAdapter = new MessageAdapter(list.getListResult(), MessageActivity.this);
                    message_listview.setAdapter(messageAdapter);
                }
                message_listview.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytes = error.networkResponse.data;
//                Log.i("woaicaojingseckill", new String(bytes));
                Toast.makeText(MessageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                map.put("PageSize", "20");
                map.put("PageIndex", String.valueOf(currentpages));
                map.put("LoginCheckToken", "123");
                map.put("LoginPhone", "123");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
