package com.aboluo.fragment.SecKillFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ConstUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.TimeUtils;
import com.aboluo.adapter.SeckillAdapter;
import com.aboluo.com.R;
import com.aboluo.com.SecKillGoodsDetailActivity;
import com.aboluo.model.SecKillAllInfo;
import com.aboluo.model.SecKillDetailInfo;
import com.aboluo.model.SecKillDetailInfo.SeckillListBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/11/24.
 */

public class CenterFragment extends Fragment {
    private SecKillAllInfo.SkillMainListBean skillMainListBean;
    private ListView seckill_listview;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private SecKillAllInfo secKillAllInfo;
    private Picasso picasso;
    private Context context;
    private SecKillDetailInfo secKillDetailInfo;
    private List<SeckillListBean> listBean;
    private SeckillAdapter seckillAdapter;
    private CountdownView cv_seckilldetail;
    private TextView txt_countview;
    private SweetAlertDialog pdialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_baseseckill, null);
        init(v);
        return v;
    }

    private void init(View v) {
        context = CenterFragment.this.getContext();
        Bundle bundle = getArguments();//从activity传过来的Bundle
        skillMainListBean = bundle.getParcelable("data");
        seckill_listview = (ListView) v.findViewById(R.id.seckill_listview);
        cv_seckilldetail = (CountdownView) v.findViewById(R.id.cv_seckilldetail);
        txt_countview = (TextView) v.findViewById(R.id.txt_countview);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(context, "ImgUrl");
        URL = CommonUtils.GetValueByKey(context, "apiurl");
        APPToken = CommonUtils.GetValueByKey(context, "APPToken");
        picasso = Picasso.with(context);
        gson = new Gson();
        pdialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSeckillDetail();
    }

    private void initSeckillDetail() {
        if (skillMainListBean == null) {
            Toast.makeText(context, "请退出刷新", Toast.LENGTH_SHORT).show();
        } else {
            pdialog.show();
            final int id = skillMainListBean.getSId();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ActiveApi/RecieveSeckillTimes", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.replace("\\", "");
                    response = response.substring(1, response.length() - 1);
                    Log.i("woaicaojingskilldetail", response);
                    secKillDetailInfo = gson.fromJson(response, SecKillDetailInfo.class);
                    if (secKillDetailInfo.isIsSuccess()) {
                        if (secKillDetailInfo.getSeckillList().size() == 0) {
                            pdialog.dismiss();
                            Toast.makeText(context, "当前没有秒杀商品", Toast.LENGTH_SHORT).show();
                        } else {
                            seckillAdapter = new SeckillAdapter(context, secKillDetailInfo.getSeckillList()
                                    , Integer.valueOf(skillMainListBean.getState()));
                            seckill_listview.setAdapter(seckillAdapter);
                            GetSreverTime(); //显示 当前秒杀状态
                        }
                    } else {
                        pdialog.dismiss();
                        Toast.makeText(context, "获取秒杀数据出错,原因：" + secKillDetailInfo.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    byte[] b = error.networkResponse.data;
                    Log.i("woaicaojingskilldetail", new String(b));
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("SeckillMainId", String.valueOf(id));
                    map.put("APPToken", APPToken);
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void GetSreverTime() {
        //(0-未开启，1-已开启，2-准备中，3-已结束）
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/api/ConfigApi/ReceiveServerTime", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(2, response.length() - 2);
                if (skillMainListBean.getState().equals("0") || skillMainListBean.getState().equals("2")) //未开始
                {
                    txt_countview.setText("距离本场开始");
                    cv_seckilldetail.start(TimeUtils.getIntervalTime(response, skillMainListBean.getStartTime(), ConstUtils.TimeUnit.MSEC));
                    cv_seckilldetail.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            initSeckillDetail();
                        }
                    });
                } else if (skillMainListBean.getState().equals("1")) {
                    txt_countview.setText("距离本场结束");
                    long time = TimeUtils.getIntervalTime(response, skillMainListBean.getStartTime(), ConstUtils.TimeUnit.MSEC);
                    cv_seckilldetail.start(time);
                    cv_seckilldetail.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                        @Override
                        public void onEnd(CountdownView cv) {
                            initSeckillDetail();
                        }
                    });
                    seckill_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("data", secKillDetailInfo.getSeckillList().get(position));
                            bundle.putString("endtime", skillMainListBean.getEndTime().toString());
                            Intent intent = new Intent(context, SecKillGoodsDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } else {
                    cv_seckilldetail.setVisibility(View.GONE);
                    txt_countview.setText("秒杀已结束");
                }
                pdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CenterFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("data", skillMainListBean);
    }


}
