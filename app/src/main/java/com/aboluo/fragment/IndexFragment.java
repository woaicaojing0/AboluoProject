package com.aboluo.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.aboluo.adapter.IndexBottomAdpater;
import com.aboluo.adapter.ThemeGridViewAdapter;
import com.aboluo.adapter.ThemeMidGridViewAdapter;
import com.aboluo.adapter.index.HotSaleGridViewAdapter;
import com.aboluo.com.GoodsDetailActivity;
import com.aboluo.com.GoodsListActivity;
import com.aboluo.com.LoginActivity;
import com.aboluo.com.MainActivity;
import com.aboluo.com.MessageActivity;
import com.aboluo.com.PartnerActivity;
import com.aboluo.com.R;
import com.aboluo.com.SecKillActivity;
import com.aboluo.com.SignInActivity;
import com.aboluo.com.UnaryActivity;
import com.aboluo.com.WebActivity.InvitationActivity;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.GoodsListInfo;
import com.aboluo.model.SecKillAllInfo;
import com.aboluo.widget.MyGridView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/9/8.
 */
public class IndexFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private LinearLayout linearLayout, ll_hotsale_main;
    private EditText top_editsearch;
    private RollPagerView rollPagerView, theme_view_pager, special_view_pager, brand_view_pager;
    private GridView mid_gridview, brand_gridview, theme_top_gridview;
    private MyGridView gv_hotsale, theme_gridview_middle, special_gridview_bottom;
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
    private BaseConfigBean indexBannerBean, huodongbean, brandConfigBean,
            themeBannerConfigBean, themeConfigBean, specialConfigBean,
            newsConfigBean, midGridViewConfigBean, themeTopConfigBean, themeBottomConfigBean, hotSaleGridViewBean, hotSaleImageBean;
    private LinearLayout linelayout_miaosha, index_message;
    private ImageView huodong_left1, huodong_left2, huodong_right1, huodong_right2, huodong_right3, theme_imageview; //1.1版本的活动页面
    private ImageView hotsale_left1, hotsale_left2, hotsale_left3, hotsale_right1, hotsale_right2;//1.2版本的特色专区
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private TextView tv_location_address;
    private GoodsListInfo listBean;
    private List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean;
    private int currentpages = 1;
    private IndexBottomAdpater indexBottomAdpater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
        } else {
        }
        view = inflater.inflate(R.layout.fragment_index, null);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (IndexFragment.this.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, BAIDU_READ_PHONE_STATE);
//            }else
//            {
//                mLocationClient = new LocationClient(IndexFragment.this.getContext());     //声明LocationClient类
//                mLocationClient.registerLocationListener(myListener);    //注册监听函数
//                initLocation();
//                mLocationClient.start();
//            }
//        } else {
//            mLocationClient = new LocationClient(IndexFragment.this.getContext());     //声明LocationClient类
//            mLocationClient.registerLocationListener(myListener);    //注册监听函数
//            initLocation();
//            mLocationClient.start();
//        }
//        LocationManager locManager = (LocationManager)IndexFragment.this.getContext().getSystemService(Context.LOCATION_SERVICE);
//        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
//        }
        init(view);
        tv_location_address.setText("定位中");
        getPersimmions();
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
        brand_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        brand_view_pager.setHintView(new ColorPointHintView(this.getActivity(), Color.RED, Color.WHITE));
        special_view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 3));
        special_view_pager.setHintView(new ColorPointHintView(this.getActivity(), Color.RED, Color.WHITE));
        brand_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 2));
//        theme_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 2));
        theme_top_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth / 2));
        mid_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (screenWidth / 2 + CommonUtils.dip2px(this.getContext(), 16))));
        ll_hotsale_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenWidth));
        initConfig();
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(IndexFragment.this.getActivity(), "1", Toast.LENGTH_SHORT).show();
            }
        });
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currentpages = 1;
                bannerAdapter.notifyDataSetChanged();
                initSecKill();
                initConfig();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                currentpages++;
                initdate(currentpages);
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
        top_editsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (top_editsearch.getText().length() == 0) {
                        Toast.makeText(IndexFragment.this.getContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        //隐藏软键盘
                        InputMethodManager inm = (InputMethodManager) IndexFragment.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inm.hideSoftInputFromWindow(top_editsearch.getWindowToken(), 0);
                        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                        intent.putExtra("GoodsName", top_editsearch.getText().toString());
                        startActivity(intent);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        //品牌点击事件
        brand_gridview.setOnItemClickListener(this);
        //特价专享点击事件
        special_gridview_bottom.setOnItemClickListener(this);
        theme_top_gridview.setOnItemClickListener(this);
        gv_hotsale.setOnItemClickListener(this);
        theme_gridview_middle.setOnItemClickListener(this);
        index_message.setOnClickListener(this);
        theme_view_pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(IndexFragment.this.getActivity(), ("" + position), Toast.LENGTH_SHORT).show();
            }
        });
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(IndexFragment.this.getActivity(), ("" + position), Toast.LENGTH_SHORT).show();
            }
        });
        special_view_pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(IndexFragment.this.getActivity(), ("" + position), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.special_gridview_bottom:  //s特价商品
//                if (themeConfigBean == null) {
//                } else {
////                    Intent intent4 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
//                    if (themeConfigBean.getAppConfigList().get(position).getParams() == null) {
//                        Toast.makeText(IndexFragment.this.getContext(),
//                                "ChildId is NULL", Toast.LENGTH_SHORT).show();
//                    } else {
////                        Toast.makeText(IndexFragment.this.getContext(),
////                                themeConfigBean.getAppConfigList().get(position).getParams().getChildId(), Toast.LENGTH_SHORT).show();
////                        intent4.putExtra("goods_type_id", themeConfigBean.getAppConfigList().get(position).getParams().getChildId());
//                        int goods_id = themeConfigBean.getAppConfigList().get(position).getParams().getChildId();
//                        Intent intent = new Intent(IndexFragment.this.getContext(), GoodsDetailActivity.class);
//                        intent.putExtra("goods_id", goods_id);
//                        startActivity(intent);
//                    }
//                    //startActivity(intent4);
//                }
                int goods_id = goodsListBean.get(position).getGoodsId();
                Intent intent = new Intent(IndexFragment.this.getContext(), GoodsDetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                String transitionName = "detail";
                ActivityOptionsCompat transitionActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(IndexFragment.this.getActivity(), view.findViewById(R.id.indexbottom_goods_image), transitionName);
                startActivity(intent, transitionActivityOptionsCompat.toBundle());
                break;
            case R.id.brand_gridview://品牌点击事件
                if (brandConfigBean == null) {
                } else {
                    Intent intent4 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (brandConfigBean.getAppConfigList().get(position).getParams() == null) {
                        Toast.makeText(IndexFragment.this.getContext(),
                                "ChildId is NULL", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IndexFragment.this.getContext(),
                                brandConfigBean.getAppConfigList().get(position).getParams().getChildId() + "", Toast.LENGTH_SHORT).show();
                        intent4.putExtra("GoodsBrandId", brandConfigBean.getAppConfigList().get(position).getParams().getChildId());
                    }
                    startActivity(intent4);
                }
                break;
            case R.id.theme_top_gridview: //主题模块上面的箱包什么的
                startGoodsListActivity(themeTopConfigBean, position);
                break;
            case R.id.gv_hotsale: //特色专区点击事件
                startGoodsListActivity(hotSaleGridViewBean, position);
                break;
            case R.id.theme_gridview_middle: //主题下面gridview4个图片的点击事件
                startGoodsListActivity(themeBottomConfigBean, position);
                break;
        }
    }

    private void startGoodsListActivity(BaseConfigBean bean, int position) {
        if (bean == null) {
        } else {
            Intent intent4 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
            if (bean.getAppConfigList().get(position).getParams() == null) {
                Toast.makeText(IndexFragment.this.getContext(),
                        "ChildId is NULL", Toast.LENGTH_SHORT).show();
            } else {
                intent4.putExtra("goods_type_id", bean.getAppConfigList().get(position).getParams().getChildId());
            }
            startActivity(intent4);
        }
    }

    /*
        初始化热卖版块的数据
     */
    private void inithotsale(View view) {
        hotsale_left1 = (ImageView) view.findViewById(R.id.hotsale_left1);
        hotsale_left2 = (ImageView) view.findViewById(R.id.hotsale_left2);
        hotsale_left3 = (ImageView) view.findViewById(R.id.hotsale_left3);
        hotsale_right1 = (ImageView) view.findViewById(R.id.hotsale_right1);
        hotsale_right2 = (ImageView) view.findViewById(R.id.hotsale_right2);
        gv_hotsale = (MyGridView) view.findViewById(R.id.gv_hotsale);
    }

    private void init(View view) {
        linearLayout = (LinearLayout) view.findViewById(R.id.ALLFather);
        top_editsearch = (EditText) view.findViewById(R.id.top_editsearch);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        theme_view_pager = (RollPagerView) view.findViewById(R.id.theme_view_pager);
        special_view_pager = (RollPagerView) view.findViewById(R.id.special_view_pager);
        brand_view_pager = (RollPagerView) view.findViewById(R.id.brand_view_pager);
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
        inithotsale(view);
        theme_imageview = (ImageView) view.findViewById(R.id.theme_imageview);
        tv_location_address = (TextView) view.findViewById(R.id.tv_location_address);
        brand_gridview = (GridView) view.findViewById(R.id.brand_gridview);
        special_gridview_bottom = (MyGridView) view.findViewById(R.id.special_gridview_bottom);
        theme_gridview_middle = (MyGridView) view.findViewById(R.id.theme_gridview_middle);
        theme_top_gridview = (GridView) view.findViewById(R.id.theme_top_gridview);
        mCvCountdownView = (CountdownView) view.findViewById(R.id.cv_countdownViewTest1);
        beginSecKill = (LinearLayout) view.findViewById(R.id.beginSecKill);
        index_message = (LinearLayout) view.findViewById(R.id.index_message);
        ll_hotsale_main = (LinearLayout) view.findViewById(R.id.ll_hotsale_main);
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

    }

    @Override
    public void onStart() {
        super.onStart();
        initSecKill();
    }

    private StringRequest getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://msoa.weidustudio.com/api/MachineManage/ReceiveProductListByCategoryId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(IndexFragment.this.getActivity(), response, Toast.LENGTH_SHORT).show();
                pullToRefreshScrollView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(IndexFragment.this.getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(IndexFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
            case R.id.hotsale_left1:
                if (hotSaleImageBean == null) {
                } else {
                    Intent intent4 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (hotSaleImageBean.getAppConfigList().get(0).getParams() == null) {
                    } else {
                        intent4.putExtra("goods_type_id", hotSaleImageBean.getAppConfigList().get(0).getParams().getChildId());
                    }
                    startActivity(intent4);
                }
                break;
            case R.id.hotsale_left2:
                if (hotSaleImageBean == null) {
                } else {
                    Intent intent5 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (hotSaleImageBean.getAppConfigList().get(1).getParams() == null) {
                    } else {
                        intent5.putExtra("goods_type_id", hotSaleImageBean.getAppConfigList().get(1).getParams().getChildId());
                    }
                    startActivity(intent5);
                }
                break;
            case R.id.hotsale_left3:
                if (hotSaleImageBean == null) {
                } else {
                    Intent intent6 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (hotSaleImageBean.getAppConfigList().get(2).getParams() == null) {
                    } else {
                        intent6.putExtra("goods_type_id", hotSaleImageBean.getAppConfigList().get(2).getParams().getChildId());
                    }
                    startActivity(intent6);
                }
                break;
            case R.id.hotsale_right1:
                if (hotSaleImageBean == null) {
                } else {
                    Intent intent7 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (hotSaleImageBean.getAppConfigList().get(3).getParams() == null) {
                    } else {
                        intent7.putExtra("goods_type_id", hotSaleImageBean.getAppConfigList().get(3).getParams().getChildId());
                    }
                    startActivity(intent7);
                }

                break;
            case R.id.hotsale_right2:
                if (hotSaleImageBean == null) {
                } else {
                    Intent intent7 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                    if (hotSaleImageBean.getAppConfigList().get(4).getParams() == null) {
                    } else {
                        intent7.putExtra("goods_type_id", hotSaleImageBean.getAppConfigList().get(4).getParams().getChildId());
                    }
                    startActivity(intent7);
                }
                break;
            case R.id.index_message:
                Intent intent8 = new Intent(IndexFragment.this.getContext(), MessageActivity.class);
                startActivity(intent8);
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
        initMemu(); //加载菜单栏
        initNews(); //加载滚动新闻
        initBanner(); //顶部banner
        //inithuodong(); //活动页面
        initHotSaleGridview(); //热卖上4个
        initHotSaleImage();//热卖下3+2
        initThemeBanner(); //主题滚动banner
        initThemeTopGridview(); //主题模块中上面的gridview
        initThemeBottomGridview();//主题模块最下面的gridview 4个图
        initBrandBanner(); //品牌banner
        initbrand();   //品牌图片
        //inittejiaGridview(); //主题九宫格
        initdate(1);
        initSpecial(); //推荐专享banner
    }

    //加载阿波罗头条
    private void initNews() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                newsConfigBean = gson.fromJson(response, BaseConfigBean.class);
                final List<String> info = new ArrayList<>();
                for (BaseConfigBean.AppConfigListBean listBean : newsConfigBean.getAppConfigList()) {
                    info.add(listBean.getName().toString());
                }
                if (info.size() == 0) {
                    info.add("暂无新闻");
                }
                marqueeView.startWithList(info);
                marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(IndexFragment.this.getActivity(), info.get(position).toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "15");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
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
                Toast.makeText(IndexFragment.this.getContext(), "专享商品获取超时，请刷新重试", Toast.LENGTH_SHORT).show();
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
        huodong_left1.setOnClickListener(this);
        huodong_left2.setOnClickListener(this);
        huodong_right1.setOnClickListener(this);
        huodong_right2.setOnClickListener(this);
        huodong_right3.setOnClickListener(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                huodongbean = gson.fromJson(response, BaseConfigBean.class);
                if (huodongbean.isIsSuccess()) {
                    String[] arrString = new String[huodongbean.getAppConfigList().size()];
                    for (int i = 0; i < huodongbean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + huodongbean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length >= 5) {
                        picasso.load(ImageUrl + huodongbean.getAppConfigList().get(0).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_left1);
                        picasso.load(ImageUrl + huodongbean.getAppConfigList().get(1).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_left2);
                        picasso.load(ImageUrl + huodongbean.getAppConfigList().get(2).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right1);
                        picasso.load(ImageUrl + huodongbean.getAppConfigList().get(3).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right2);
                        picasso.load(ImageUrl + huodongbean.getAppConfigList().get(4).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(huodong_right3);
                    } else {

                    }
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), huodongbean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
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

    /*
        加载商品热卖专区的九宫格图
     */
    private void initHotSaleGridview() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                hotSaleGridViewBean = gson.fromJson(response, BaseConfigBean.class);
                if (hotSaleGridViewBean.isIsSuccess()) {
                    String[] arrString = new String[hotSaleGridViewBean.getAppConfigList().size()];
                    for (int i = 0; i < hotSaleGridViewBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + hotSaleGridViewBean.getAppConfigList().get(i).getImage();
                    }

                    if (arrString.length == 0) {
                        gv_hotsale.setVisibility(View.GONE);
                    } else {
                        gv_hotsale.setVisibility(View.VISIBLE);
                        HotSaleGridViewAdapter hotSaleGridViewAdapter = new HotSaleGridViewAdapter(IndexFragment.this.getContext(), arrString);
                        gv_hotsale.setAdapter(hotSaleGridViewAdapter);
                    }

                } else {
                    Toast.makeText(IndexFragment.this.getContext(), hotSaleGridViewBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "11");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /*
        加载商品热卖专区的3+2图片
     */
    private void initHotSaleImage() {
        hotsale_left1.setOnClickListener(this);
        hotsale_left2.setOnClickListener(this);
        hotsale_left3.setOnClickListener(this);
        hotsale_right1.setOnClickListener(this);
        hotsale_right2.setOnClickListener(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                hotSaleImageBean = gson.fromJson(response, BaseConfigBean.class);
                if (hotSaleImageBean.isIsSuccess()) {
                    String[] arrString = new String[hotSaleImageBean.getAppConfigList().size()];
                    for (int i = 0; i < hotSaleImageBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + hotSaleImageBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length >= 5) {
                        ll_hotsale_main.setVisibility(View.VISIBLE);
                        picasso.load(ImageUrl + hotSaleImageBean.getAppConfigList().get(0).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(hotsale_left1);
                        picasso.load(ImageUrl + hotSaleImageBean.getAppConfigList().get(1).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(hotsale_left2);
                        picasso.load(ImageUrl + hotSaleImageBean.getAppConfigList().get(2).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(hotsale_left3);
                        picasso.load(ImageUrl + hotSaleImageBean.getAppConfigList().get(3).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(hotsale_right1);
                        picasso.load(ImageUrl + hotSaleImageBean.getAppConfigList().get(4).getImage())
                                .placeholder(IndexFragment.this.getResources().getDrawable(R.drawable.imagviewloading))
                                .into(hotsale_right2);
                    } else {
                        ll_hotsale_main.setVisibility(View.GONE);
                        Toast.makeText(IndexFragment.this.getContext(), "特色专区图片少于5个", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), hotSaleImageBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "13");
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
                themeBannerConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (themeBannerConfigBean.isIsSuccess()) {
                    String[] arrString = new String[themeBannerConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < themeBannerConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + themeBannerConfigBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length == 0) {
                        theme_view_pager.setVisibility(View.GONE);
                    } else {
                        //头部滚动banner
                        theme_view_pager.setVisibility(View.VISIBLE);
                        bannerAdapter = new BannerAdapter(IndexFragment.this.getContext(), arrString, theme_view_pager);
                        theme_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                        theme_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                        theme_view_pager.setFocusable(false);
                    }
                } else {
                    theme_view_pager.setVisibility(View.GONE);
                    Toast.makeText(IndexFragment.this.getContext(), themeBannerConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "14");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载主题最上面gridview的配置（活动2）//加载主题九宫格
    private void initThemeTopGridview() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                themeTopConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (themeTopConfigBean.isIsSuccess()) {
                    String[] arrString = new String[themeTopConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < themeTopConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + themeTopConfigBean.getAppConfigList().get(i).getImage();
                    }

                    if (arrString.length == 0) {
                        theme_top_gridview.setVisibility(View.GONE);
                    } else {
                        theme_top_gridview.setVisibility(View.VISIBLE);
                        ThemeMidGridViewAdapter themeMidGridViewAdapter = new ThemeMidGridViewAdapter(IndexFragment.this.getContext(), arrString);
                        theme_top_gridview.setAdapter(themeMidGridViewAdapter);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
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

    //加载主题最下面gridview的配置（4个图片）
    private void initThemeBottomGridview() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                themeBottomConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (themeBottomConfigBean.isIsSuccess()) {
                    String[] arrString = new String[themeBottomConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < themeBottomConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + themeBottomConfigBean.getAppConfigList().get(i).getImage();
                    }

                    if (arrString.length == 0) {
                        theme_gridview_middle.setVisibility(View.GONE);
                    } else {
                        theme_gridview_middle.setVisibility(View.VISIBLE);
                        ThemeMidGridViewAdapter themeMidGridViewAdapter = new ThemeMidGridViewAdapter(IndexFragment.this.getContext(), arrString);
                        theme_gridview_middle.setAdapter(themeMidGridViewAdapter);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "12");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载品类活动banner的配置
    private void initBrandBanner() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                themeBannerConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (themeBannerConfigBean.isIsSuccess()) {
                    String[] arrString = new String[themeBannerConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < themeBannerConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + themeBannerConfigBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length == 0) {
                        brand_view_pager.setVisibility(View.GONE);
                    } else {
                        //头部滚动banner
                        brand_view_pager.setVisibility(View.VISIBLE);
                        bannerAdapter = new BannerAdapter(IndexFragment.this.getContext(), arrString, brand_view_pager);
                        brand_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                        brand_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                        brand_view_pager.setFocusable(false);
                    }
                } else {
                    brand_view_pager.setVisibility(View.GONE);
                    Toast.makeText(IndexFragment.this.getContext(), themeBannerConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
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

    //加载品牌的配置
    private void initbrand() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                brandConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (brandConfigBean.isIsSuccess()) {
                    String[] arrString = new String[brandConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < brandConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + brandConfigBean.getAppConfigList().get(i).getImage();
                    }
                    BrandGridViewAdapter brandGridViewAdapter = new BrandGridViewAdapter(IndexFragment.this.getContext(), arrString);
                    brand_gridview.setAdapter(brandGridViewAdapter);
                } else {
                    Toast.makeText(IndexFragment.this.getContext(), brandConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
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

    //加载特价专享
    private void inittejiaGridview() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                themeConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (themeConfigBean.isIsSuccess()) {
                    String[] arrString = new String[themeConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < themeConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + themeConfigBean.getAppConfigList().get(i).getImage();
                    }

                    if (arrString.length == 0) {
                        special_gridview_bottom.setVisibility(View.GONE);
                    } else {
                        special_gridview_bottom.setVisibility(View.VISIBLE);
                        ThemeGridViewAdapter themeGridViewAdapter = new ThemeGridViewAdapter(IndexFragment.this.getContext(), arrString);
                        special_gridview_bottom.setAdapter(themeGridViewAdapter);
                    }

                } else {
//                    if (themeBannerConfigBean.isIsSuccess()) {
//                        String[] arrString2 = new String[themeBannerConfigBean.getAppConfigList().size()];
//                        if (arrString2.length == 0) { //如果主题的banner 和主题的九宫格都没有，则隐藏主题导购
//                            theme_imageview.setVisibility(View.GONE);
//                        } else {
//                            theme_imageview.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        theme_imageview.setVisibility(View.GONE);
//                        theme_gridview.setVisibility(View.GONE);
//                        Toast.makeText(IndexFragment.this.getContext(), themeConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "10");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载特价专享2
    private void initdate(final int currentpage) {
        StringRequest requestlist = new StringRequest(Request.Method.POST, URL + "/api/Goods/ReceiveGoodsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Gson gson = new Gson();
                Log.i("woaicaojing", URL + "/api/Goods/ReceiveGoodsList");
                Log.i("woaicaojing", response);
                listBean = gson.fromJson(response, GoodsListInfo.class);
                if (listBean.getResult().getGoodsList().size() == 0) {
                    Toast.makeText(IndexFragment.this.getContext(), "当前没有数据啦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentpage == 1) {
                    goodsListBean = listBean.getResult().getGoodsList();
                    indexBottomAdpater = new IndexBottomAdpater(IndexFragment.this.getContext(),
                            goodsListBean);
                    special_gridview_bottom.setAdapter(indexBottomAdpater);
                } else {
                    if (goodsListBean == null) {
                        goodsListBean = listBean.getResult().getGoodsList();
                        indexBottomAdpater = new IndexBottomAdpater(IndexFragment.this.getContext(),
                                goodsListBean);
                        special_gridview_bottom.setAdapter(indexBottomAdpater);
                    } else {
                        List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean2 = listBean.getResult().getGoodsList();
                        goodsListBean.addAll(goodsListBean2);
                        special_gridview_bottom.setAdapter(indexBottomAdpater);
                        pullToRefreshScrollView.onRefreshComplete();
                    }
                }
                pdialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                byte[] htmlBodyBytes = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                Log.i("woaiocaojingerroe", new String(htmlBodyBytes));
                // Log.i("woaicaojing", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("APPToken", APPToken);
                map.put("CurrentPage", String.valueOf(currentpage));
                map.put("PageSize", "20");
                map.put("IsShowHomePage", "1");
                return map;
            }
        };
        requestQueue.add(requestlist);
    }

    //加载推荐专享配置
    private void initSpecial() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                specialConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (specialConfigBean.isIsSuccess()) {
                    String[] arrString = new String[specialConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < specialConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + specialConfigBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length == 0) {
                        theme_view_pager.setVisibility(View.GONE);
                    } else {
                        //头部滚动banner
                        special_view_pager.setVisibility(View.VISIBLE);
                        bannerAdapter = new BannerAdapter(IndexFragment.this.getContext(), arrString, special_view_pager);
                        special_view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                        special_view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                        special_view_pager.setFocusable(false);
                    }
                } else {
                    special_view_pager.setVisibility(View.GONE);
                    Toast.makeText(IndexFragment.this.getContext(), specialConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                pullToRefreshScrollView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pullToRefreshScrollView.onRefreshComplete();
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "7");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    //加载首页菜单栏配置
    private void initMemu() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                midGridViewConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (midGridViewConfigBean.isIsSuccess()) {
                    Collections.sort(midGridViewConfigBean.getAppConfigList(), new Comparator<BaseConfigBean.AppConfigListBean>() {

                        @Override
                        public int compare(BaseConfigBean.AppConfigListBean arg0, BaseConfigBean.AppConfigListBean arg1) {
                            return arg0.getParams().getParentId().compareTo(arg1.getParams().getParentId());
                        }
                    });
                    String[] arrString = new String[midGridViewConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < midGridViewConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + midGridViewConfigBean.getAppConfigList().get(i).getImage();
                    }
                    if (arrString.length == 0) {
                        theme_view_pager.setVisibility(View.GONE);
                    } else {
                        initMidGridView(arrString);
                    }
                } else {
                    mid_gridview.setVisibility(View.GONE);
                    Toast.makeText(IndexFragment.this.getContext(), midGridViewConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pullToRefreshScrollView.onRefreshComplete();
                //byte[] data = error.networkResponse.data;
                //Toast.makeText(IndexFragment.this.getContext(), new String(data), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("ConfigModule", "2");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void initMidGridView(String[] images) {
        mid_gridview.setAdapter(new GridViewAdapter(this.getActivity(), images));
        mid_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(IndexFragment.this.getActivity(), position + "", Toast.LENGTH_SHORT).show();
                switch (midGridViewConfigBean.getAppConfigList().get(position).getParams().getChildId()) {
                    case 53:
                        Intent intent = new Intent(getActivity(), PartnerActivity.class);
                        startActivity(intent);
                        break;
                    case 51:
                        Intent intent1 = new Intent(getActivity(), UnaryActivity.class);
                        startActivity(intent1);
                        break;
                    case 52:
                        Intent intent2 = new Intent(getActivity(), MainActivity.class);
                        intent2.putExtra("id", 2);
                        startActivity(intent2);
                        break;
                    case 54:
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
                    case 56:
                        Intent intent5 = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent5);
                        break;
                    case 59: //9.9专场
                        Intent intent6 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                        intent6.putExtra("goods_type_id", 297);
                        startActivity(intent6);
                        break;
                    case 60: //最新商品
                        Intent intent7 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                        startActivity(intent7);
                        break;
                    case 61: //年货节
                        Intent intent8 = new Intent(IndexFragment.this.getContext(), GoodsListActivity.class);
                        intent8.putExtra("goods_type_id", 271);
                        startActivity(intent8);
                        break;
                    case 57:
                        if (CommonUtils.IsLogin(IndexFragment.this.getContext())) {
                            Intent intent14 = new Intent(IndexFragment.this.getActivity(), InvitationActivity.class);
                            startActivity(intent14);
                        } else {
                            Intent intent14 = new Intent(IndexFragment.this.getActivity(), LoginActivity.class);
                            intent14.putExtra("fromHome", 1);
                            Toast.makeText(IndexFragment.this.getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                            startActivity(intent14);
                        }
                        break;
                }
            }
        });
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation
                    || location.getLocType() == BDLocation.TypeOffLineLocation) {
                tv_location_address.setText(location.getCity().toString());
            } else {
                Toast.makeText(IndexFragment.this.getContext(), "定位失败", Toast.LENGTH_SHORT).show();
                tv_location_address.setText("");
            }
            //Receive Location
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());// 单位：公里每小时
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());
//                sb.append("\nheight : ");
//                sb.append(location.getAltitude());// 单位：米
//                sb.append("\ndirection : ");
//                sb.append(location.getDirection());// 单位度
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//                location.getCity()
//
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                //运营商信息
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功，离线定位结果也是有效的");
//            } else if (location.getLocType() == BDLocation.TypeServerError) {
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
//            }
//            sb.append("\nlocationdescribe : ");
//            sb.append(location.getLocationDescribe());// 位置语义化信息
//            List<Poi> list = location.getPoiList();// POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
//            Log.i("BaiduLocationApiDem", sb.toString());
            mLocationClient.stop();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationClient = new LocationClient(IndexFragment.this.getContext());     //声明LocationClient类
                    mLocationClient.registerLocationListener(myListener);    //注册监听函数
                    initLocation();
                    mLocationClient.start();
                } else {
                    // 没有获取到权限，做特殊处理
                }
                break;
            default:
                break;
        }
    }

    /*
    *判断当前是否是6.0版本
    */
    @TargetApi(23)
    private void getPersimmions() {
        ////判断当前是否是6.0版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (IndexFragment.this.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

            }
            if (IndexFragment.this.getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), BAIDU_READ_PHONE_STATE);
            } else {//****这里写自己的定位调用***/////
                ///当权限都有时，开始定位
                mLocationClient = new LocationClient(IndexFragment.this.getContext());     //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);    //注册监听函数
                initLocation();
                mLocationClient.start();
            }
        } else {
//****这里写自己的定位调用***/////
            ///低于6.0以下，开始定位
            mLocationClient = new LocationClient(IndexFragment.this.getContext());     //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);    //注册监听函数
            initLocation();
            mLocationClient.start();
        }


    }


}
