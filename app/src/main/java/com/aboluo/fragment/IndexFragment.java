package com.aboluo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ConstUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.XUtils.TimeUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.BrandGridViewAdapter;
import com.aboluo.adapter.GridViewAdapter;
import com.aboluo.adapter.ThemeGridViewAdapter;
import com.aboluo.com.HeHuoRenActivity;
import com.aboluo.com.MainActivity;
import com.aboluo.com.R;
import com.aboluo.com.SecKillActivity;
import com.aboluo.com.SignInActivity;
import com.aboluo.com.UnaryActivity;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.SecKillAllInfo;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class IndexFragment extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout;
    private EditText top_editsearch;
    private RollPagerView rollPagerView, theme_view_pager;
    private GridView mid_gridview;
    private MarqueeView marqueeView;
    private View view;
    private String[] imgurl = {"http://img4.imgtn.bdimg.com/it/u=2408370625,380818695&fm=21&gp=0.jpg", "" +
            "http://pic24.nipic.com/20121025/10444819_041559015351_2.jpg"};
    private PullToRefreshScrollView pullToRefreshScrollView;
    private BannerAdapter bannerAdapter;
    private ImageView ceshi_imgeview, seckill_imge1, seckill_imge0, seckill_imge2;
    private CountdownView mCvCountdownView;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private LinearLayout beginSecKill;
    private Gson gson;
    private SecKillAllInfo secKillAllInfo;
    private ArrayList<SecKillAllInfo.SkillMainListBean> listskillbean;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private BaseConfigBean indexBannerBean;
    private LinearLayout linelayout_miaosha;
    private ImageView huodong_left1, huodong_left2, huodong_right1, huodong_right2, huodong_right3;
    private GridView brand_gridview, theme_gridview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
        } else {
        }
        view = inflater.inflate(R.layout.fragment_index, null);
        init(view);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linearLayout.setFocusable(true);
                linearLayout.setFocusableInTouchMode(true);
                linearLayout.requestFocus();
                return false;
            }
        });
//        //edittext 中的图标
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        drawable.setBounds(0, 0, 80, 80);
//        top_editsearch.setCompoundDrawables(drawable, null, drawable, null);
        //窗口的宽度
        int screenWidth = ScreenUtils.getScreenWidth(this.getContext());
        //设置顶部banner 的高度 高度是宽度的1/3；
        rollPagerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        rollPagerView.setHintView(new ColorPointHintView(this.getActivity(), Color.RED, Color.WHITE));
        theme_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        theme_view_pager.setHintView(new ColorPointHintView(this.getActivity(), Color.RED, Color.WHITE));
        brand_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 2));
        theme_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 2));
        initConfig();
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
            }
        });
        mid_gridview.setAdapter(new GridViewAdapter(this.getActivity()));
        mid_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(IndexFragment.this.getActivity(), position + "", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getActivity(), HeHuoRenActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), UnaryActivity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        Intent intent2 = new Intent(getActivity(), MainActivity.class);
                        intent2.putExtra("id", 2);
                        startActivity(intent2);
                        break;
                    case 4:
                        if (secKillAllInfo == null) {
                        } else {
                            if (secKillAllInfo.getResult().equals("暂无秒杀场次")) {
                                Toast.makeText(IndexFragment.this.getContext(), "暂无秒杀场次", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent SecKillintent = new Intent(IndexFragment.this.getContext(), SecKillActivity.class);
                                SecKillintent.putExtra("data", listskillbean);
                                SecKillintent.putExtra("changci", 0);
                                startActivity(SecKillintent);
                            }
                        }
                        break;
                    case 5:
                        Intent intent5 = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent5);
                        break;

                }
            }
        });
        final List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是曹晶 \n 2.欢迎大家关注我哦！");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：woaicaojing0");
        info.add("4. 新浪微博：曹晶微博");
        info.add("5. 个人博客：caojing.com");
        info.add("6. 微信公众号：woaiocajing0");
        marqueeView.startWithList(info);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(IndexFragment.this.getActivity(), info.get(position).toString() + textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                bannerAdapter.notifyDataSetChanged();
                requestQueue.add(getInfo());
            }
        });
//        Picasso.with(this.getActivity()).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1309/05/c5/25283777_1378352004384_800x600.jpg").into(ceshi_imgeview);
////            Picasso.with(this.getActivity()).setIndicatorsEnabled(true);
        mCvCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
//                cv.setVisibility(View.GONE);
                initSecKill();
            }
        });
        mCvCountdownView.setOnCountdownIntervalListener(3000, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
//                Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
            }
        });
//        ceshi_imgeview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
//                Intent intent1 = new Intent(getActivity(), GoodsListActivity.class);
//                intent.putExtra("info", "这是从首页中的图片跳转的");
//                startActivity(intent1);
//
//            }
//        });
        top_editsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String s = top_editsearch.getText().toString().trim();
                    Toast.makeText(IndexFragment.this.getContext(), s, Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void init(View view) {
        linearLayout = (LinearLayout) view.findViewById(R.id.ALLFather);
        top_editsearch = (EditText) view.findViewById(R.id.top_editsearch);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        theme_view_pager = (RollPagerView) view.findViewById(R.id.theme_view_pager);
        mid_gridview = (GridView) view.findViewById(R.id.mid_gridview);
        mid_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pullToRefresh);
        seckill_imge0 = (ImageView) view.findViewById(R.id.seckill_imge0);
        seckill_imge1 = (ImageView) view.findViewById(R.id.seckill_imge1);
        seckill_imge2 = (ImageView) view.findViewById(R.id.seckill_imge2);
        huodong_left1 = (ImageView) view.findViewById(R.id.huodong_left1);
        huodong_left2 = (ImageView) view.findViewById(R.id.huodong_left2);
        huodong_right1 = (ImageView) view.findViewById(R.id.huodong_right1);
        huodong_right2 = (ImageView) view.findViewById(R.id.huodong_right2);
        huodong_right3 = (ImageView) view.findViewById(R.id.huodong_right3);
        brand_gridview = (GridView) view.findViewById(R.id.brand_gridview);
        theme_gridview = (GridView) view.findViewById(R.id.theme_gridview);
        mCvCountdownView = (CountdownView) view.findViewById(R.id.cv_countdownViewTest1);
        beginSecKill = (LinearLayout) view.findViewById(R.id.beginSecKill);
        linelayout_miaosha = (LinearLayout) view.findViewById(R.id.linelayout_miaosha);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(IndexFragment.this.getContext(), "ImgUrl");
        URL = CommonUtils.GetValueByKey(IndexFragment.this.getContext(), "apiurl");
        APPToken = CommonUtils.GetValueByKey(IndexFragment.this.getContext(), "APPToken");
        picasso = Picasso.with(IndexFragment.this.getContext());
        gson = new Gson();
        pdialog = new SweetAlertDialog(IndexFragment.this.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        int screenWidth = ScreenUtils.getScreenWidth(IndexFragment.this.getContext());
        beginSecKill.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        beginSecKill.setOnClickListener(this);
        seckill_imge0.setOnClickListener(this);
        seckill_imge1.setOnClickListener(this);
        seckill_imge2.setOnClickListener(this);
        initSecKill();

    }


    private StringRequest getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://msoa.weidustudio.com/api/MachineManage/ReceiveProductListByCategoryId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(IndexFragment.this.getActivity(), response, Toast.LENGTH_SHORT).show();
                pullToRefreshScrollView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndexFragment.this.getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                pullToRefreshScrollView.onRefreshComplete();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ProductCategoryId", "1");
                map.put("PageIndex", "1");
                return map;
            }
        };
        return stringRequest;
    }

    private void initSecKill() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ActiveApi/RecieveMainSeckillList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                seckill_imge0.setVisibility(View.VISIBLE);
                seckill_imge1.setVisibility(View.VISIBLE);
                seckill_imge2.setVisibility(View.VISIBLE);
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                secKillAllInfo = gson.fromJson(response, SecKillAllInfo.class);
                if (secKillAllInfo.getResult().equals("暂无秒杀场次")) {
                    linelayout_miaosha.setVisibility(View.GONE);
                    seckill_imge0.setVisibility(View.GONE);
                    seckill_imge1.setVisibility(View.GONE);
                    seckill_imge2.setVisibility(View.GONE);
                    mCvCountdownView.setVisibility(View.GONE);
                    //Toast.makeText(IndexFragment.this.getContext(), "暂无秒杀场次", Toast.LENGTH_SHORT).show();
                } else {
                    linelayout_miaosha.setVisibility(View.VISIBLE);
                    listskillbean = (ArrayList<SecKillAllInfo.SkillMainListBean>) secKillAllInfo.getSkillMainList();
                    GetSreverTime();
                }

                Log.i("woaicaojingseckill", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] bytes = error.networkResponse.data;
//                Log.i("woaicaojingseckill", new String(bytes));
                Toast.makeText(IndexFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetSreverTime() {
        //(0-未开启，1-已开启，2-准备中，3-已结束）
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + "/api/ConfigApi/ReceiveServerTime", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(2, response.length() - 2);
                String beginedTime = null;
                String whilebeginTime = null;
                long time = 0;
                for (int i = 0; i < listskillbean.size(); i++) {
                    if (listskillbean.get(i).getState().equals("1")) {
                        beginedTime = listskillbean.get(i).getEndTime().toString();
                    } else if (listskillbean.get(i).getState().equals("2")) {
                        if (whilebeginTime == null) {
                            whilebeginTime = listskillbean.get(i).getStartTime().toString();
                        } else {
                        }
                    } else {
                    }
                }
                if (beginedTime != null) {
                    Date date = TimeUtils.string2Date(beginedTime);
                    Date date2 = TimeUtils.string2Date(response);
                    time = TimeUtils.getIntervalTime(date, date2, ConstUtils.TimeUnit.MSEC);
                } else if (whilebeginTime != null) {
                    time = TimeUtils.getIntervalTime(response, whilebeginTime, ConstUtils.TimeUnit.MSEC);
                } else {
                }
                mCvCountdownView.start(time); // 毫秒
                InitSeckillImage(listskillbean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndexFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seckill_imge0:
                Intent intent = new Intent(IndexFragment.this.getContext(), SecKillActivity.class);
                intent.putExtra("data", listskillbean);
                intent.putExtra("changci", 0);
                startActivity(intent);
                break;
            case R.id.seckill_imge1:
                Intent intent2 = new Intent(IndexFragment.this.getContext(), SecKillActivity.class);
                intent2.putExtra("data", listskillbean);
                intent2.putExtra("changci", 1);
                startActivity(intent2);
                break;
            case R.id.seckill_imge2:
                Intent intent3 = new Intent(IndexFragment.this.getContext(), SecKillActivity.class);
                intent3.putExtra("data", listskillbean);
                intent3.putExtra("changci", 2);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    private void InitSeckillImage(List<SecKillAllInfo.SkillMainListBean> listskillbean) {
        if (listskillbean.size() >= 3) {
            Log.i("woaicaojingseckill", ImageUrl + listskillbean.get(1).getImage());
            picasso.load(ImageUrl + listskillbean.get(0).getImage())
                    .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(IndexFragment.this.getResources().getDrawable(R.drawable.imageview_error))
                    .into(seckill_imge0);
            picasso.load(ImageUrl + listskillbean.get(1).getImage())
                    .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(IndexFragment.this.getResources().getDrawable(R.drawable.imageview_error))
                    .into(seckill_imge1);
            picasso.load(ImageUrl + listskillbean.get(2).getImage())
                    .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                    .error(IndexFragment.this.getResources().getDrawable(R.drawable.imageview_error))
                    .into(seckill_imge2);
        } else {
            Toast.makeText(IndexFragment.this.getContext(), "当前秒杀场次不够三场", Toast.LENGTH_SHORT).show();
        }
    }

    private void initConfig() {
        initBanner(); //顶部banner
        inithuodong(); //活动页面
        initbrand();   //品牌图片
        initThemeBanner(); //主题滚动banner
        initThemeGridview(); //主题九宫格
    }

    //加载首页的配置
    private void initBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                indexBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (indexBannerBean.isIsSuccess()) {
                    String[] arrString = new String[indexBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < indexBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + indexBannerBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(IndexFragment.this.getContext(), arrString, rollPagerView);
                    rollPagerView.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    rollPagerView.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    rollPagerView.setFocusable(false);
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), indexBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] data = error.networkResponse.data;
                //  Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "1");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }

    //加载活动的配置
    private void inithuodong() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                indexBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (indexBannerBean.isIsSuccess()) {
                    String[] arrString = new String[indexBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < indexBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + indexBannerBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length >= 5) {
                        picasso.load(ImageUrl + indexBannerBean.getAppConfigList().get(0).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_left1);
                        picasso.load(ImageUrl + indexBannerBean.getAppConfigList().get(1).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_left2);
                        picasso.load(ImageUrl + indexBannerBean.getAppConfigList().get(2).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right1);
                        picasso.load(ImageUrl + indexBannerBean.getAppConfigList().get(3).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right2);
                        picasso.load(ImageUrl + indexBannerBean.getAppConfigList().get(4).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right3);
                    } else {

                    }
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), indexBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "3");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载品牌的配置
    private void initbrand() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                indexBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (indexBannerBean.isIsSuccess()) {
                    String[] arrString = new String[indexBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < indexBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + indexBannerBean.getAppConfigList().get(i).getImage();
                    }
                    BrandGridViewAdapter brandGridViewAdapter = new BrandGridViewAdapter(IndexFragment.this.getContext(), arrString);
                    brand_gridview.setAdapter(brandGridViewAdapter);
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), indexBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "6");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载主题导购banner的配置
    private void initThemeBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                indexBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (indexBannerBean.isIsSuccess()) {
                    String[] arrString = new String[indexBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < indexBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + indexBannerBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(IndexFragment.this.getContext(), arrString, theme_view_pager);
                    theme_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    theme_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    theme_view_pager.setFocusable(false);
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), indexBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "4");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载品牌九宫图的配置
    private void initThemeGridview() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                indexBannerBean = gson.fromJson(response, BaseConfigBean.class);
                if (indexBannerBean.isIsSuccess()) {
                    String[] arrString = new String[indexBannerBean.getAppConfigList().size()];
                    for (int i = 0; i < indexBannerBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + indexBannerBean.getAppConfigList().get(i).getImage();
                    }
                    ThemeGridViewAdapter themeGridViewAdapter = new ThemeGridViewAdapter(IndexFragment.this.getContext(), arrString);
                    theme_gridview.setAdapter(themeGridViewAdapter);
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), indexBannerBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] data = error.networkResponse.data;
                Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "5");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
