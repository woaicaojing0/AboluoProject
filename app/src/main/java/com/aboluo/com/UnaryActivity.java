package com.aboluo.com;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.Interface.OnRecyclerViewItemClickListener;
import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.ThreeImageAdapter;
import com.aboluo.adapter.UnaryAdapter;
import com.aboluo.model.BaseConfigBean;
import com.aboluo.model.ShopCarBean;
import com.aboluo.model.UnaryListBean;
import com.aboluo.widget.FullyGridLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJ on 2016/12/2.
 */

public class UnaryActivity extends FragmentActivity implements
        UnaryAdapter.OnRecyclerViewItemClickListener, View.OnClickListener, OnRecyclerViewItemClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Context mcontext;
    private Picasso picasso;
    private Gson gson;
    private BaseConfigBean unaryConfigBean;
    private BannerAdapter bannerAdapter;
    private RollPagerView unary__view_pager;
    private TextView tv_unary_introduce;
    private RBCallbkRecyclerView unary_recyclerView;
    private UnaryAdapter adapter;
    private UnaryListBean unaryListBean;
    private TextView unary_popularity, unary_new, unary_introduce;
    private int currentpage = 1;
    private String sort = null;
    private String sorttype = null;
    private WebView webview_introduce;
    private LinearLayout unary_publish;
    private ArrayList<ShopCarBean.ResultBean.GoodsShoppingCartListBean> goodsShoppingCartListBean; //传入下订单的信息
    private ImageView iv_unary_back;
    private LargeImageView lgiv_unary_introduce;
    private List<UnaryListBean.ListResultBean> listResult;
    private List<UnaryListBean.ListResultBean> threeImageListResult;
    private RecyclerView recycle_unary_threeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unary_index);
        init();
        unary_recyclerView.setReachBottomRow(4);
        unary_recyclerView.setOnReachBottomListener(new RBCallbkRecyclerView.OnReachBottomListener() {
            @Override
            public void onReachBottom() {
                //即将到达几部，进行加载更多操作
                // Toast.makeText(mcontext, "sssssssssssssssssssssssss", Toast.LENGTH_SHORT).show();
                if (sort == null || sorttype == null) {
                } else {
                    currentpage++;
                    GetUnaryListBean("2", currentpage, sort, sorttype);
                }

            }
        });
        unary_popularity.setOnClickListener(this);
        unary_new.setOnClickListener(this);
        unary_introduce.setOnClickListener(this);
        unary_publish.setOnClickListener(this);
        iv_unary_back.setOnClickListener(this);
        tv_unary_introduce.setOnClickListener(this);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        unary__view_pager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (screenWidth) * 2 / 5));
        unary__view_pager.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        try {
            lgiv_unary_introduce.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("unaryintroduce.png")));
            lgiv_unary_introduce.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        mcontext = this;
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(mcontext, "ImgUrl");
        URL = CommonUtils.GetValueByKey(mcontext, "apiurl");
        APPToken = CommonUtils.GetValueByKey(mcontext, "APPToken");
        gson = new Gson();
        picasso = Picasso.with(mcontext);
        unary__view_pager = (RollPagerView) findViewById(R.id.unary__view_pager);

        iv_unary_back = (ImageView) findViewById(R.id.iv_unary_back);
        unary_popularity = (TextView) findViewById(R.id.unary_popularity);
        unary_new = (TextView) findViewById(R.id.unary_new);
        unary_introduce = (TextView) findViewById(R.id.unary_introduce);
        tv_unary_introduce = (TextView) findViewById(R.id.tv_unary_introduce);
        unary_recyclerView = (RBCallbkRecyclerView) findViewById(R.id.unary_recyclerView);
        webview_introduce = (WebView) findViewById(R.id.webview_introduce);
        unary_publish = (LinearLayout) findViewById(R.id.unary_publish);
        lgiv_unary_introduce = (LargeImageView) findViewById(R.id.lgiv_unary_introduce);
        recycle_unary_threeImage = (RecyclerView) findViewById(R.id.recycle_unary_threeImage);
        unary_popularity.setTextColor(UnaryActivity.this.getResources().getColor(R.color.btn_color));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle_unary_threeImage.setLayoutManager(linearLayoutManager);
        initBannerImage();
        initThreeImageUrl();
        InitUnaryListBean();
        initWebview();
        goodsShoppingCartListBean = new ArrayList<>();
    }

    private void initWebview() {
        webview_introduce.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview_introduce.setVerticalScrollBarEnabled(false);
        webview_introduce.setVerticalScrollbarOverlay(false);
        webview_introduce.setHorizontalScrollBarEnabled(false);
        webview_introduce.setHorizontalScrollbarOverlay(false);
        //end
        WebSettings webviewsetting = webview_introduce.getSettings();
        webviewsetting.setJavaScriptEnabled(true);
        webviewsetting.setUseWideViewPort(true);//关键点
        webviewsetting.setLoadWithOverviewMode(true);
        webview_introduce.loadUrl("www.ly.com");
        webview_introduce.setWebViewClient(new WebViewClient());
    }

    private void initBannerImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ConfigApi/ReceiveHomeConfig", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryConfigBean = gson.fromJson(response, BaseConfigBean.class);
                if (unaryConfigBean.isIsSuccess()) {
                    String[] arrString = new String[unaryConfigBean.getAppConfigList().size()];
                    for (int i = 0; i < unaryConfigBean.getAppConfigList().size(); i++) {
                        arrString[i] = ImageUrl + unaryConfigBean.getAppConfigList().get(i).getImage();
                    }
                    //头部滚动banner
                    bannerAdapter = new BannerAdapter(mcontext, arrString, unary__view_pager);
                    unary__view_pager.setAdapter(bannerAdapter); // 设置适配器（请求网络图片，适配器要在网络请求完成后再设置）
                    unary__view_pager.getViewPager().getAdapter().notifyDataSetChanged();// 更新banner图片
                    unary__view_pager.setFocusable(false);
                } else {
                    Toast.makeText(mcontext, unaryConfigBean.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                map.put("ConfigModule", "8");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 加载显示的正在揭晓的一元购
     */
    private void initThreeImageUrl() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    threeImageListResult = unaryListBean.getListResult();
                    ThreeImageAdapter threeImageAdapter = new ThreeImageAdapter(UnaryActivity.this, threeImageListResult);
                    recycle_unary_threeImage.setAdapter(threeImageAdapter);
                    threeImageAdapter.setOnRecyclerViewItemClickListener(UnaryActivity.this);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String result = new String(error.networkResponse.data);
                Toast.makeText(mcontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //最先开奖的放在前面
                Map<String, String> map = new HashMap<>();
                map.put("State", "2");
                map.put("IsPaging", "true");
                map.put("CurrentPage", "1");
                map.put("PageSize", "5");
                map.put("SortValue", "finishTime");
                map.put("SortType", "desc");
                map.put("TopCount", "3");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(mcontext, "当前点击项是：" + position, Toast.LENGTH_SHORT).show();
        UnaryListBean.ListResultBean listResultBean = unaryListBean.getListResult().get(position);
        Intent intent = new Intent(UnaryActivity.this, UnaryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", listResultBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (v.getId()) {
            case R.id.unary_popularity: // 人气
                CleanTxtView();
                unary_popularity.setTextColor(this.getResources().getColor(R.color.btn_color));
                currentpage = 1;
                sort = "joinCount";
                sorttype = "desc";
                GetUnaryListBean("1", currentpage, sort, sorttype);
                break;
            case R.id.unary_new: //最新
                CleanTxtView();
                unary_new.setTextColor(this.getResources().getColor(R.color.btn_color));
                currentpage = 1;
                sort = "StartTime";
                sorttype = "desc";
                GetUnaryListBean("1", currentpage, sort, sorttype);
                break;
            case R.id.unary_introduce: //玩法介绍
                CleanTxtView();
                webview_introduce.setVisibility(View.GONE);
                unary_recyclerView.setVisibility(View.GONE);
                unary_introduce.setTextColor(this.getResources().getColor(R.color.btn_color));
                lgiv_unary_introduce.setVisibility(View.VISIBLE);
                break;
            case R.id.unary_publish:
                Intent intent = new Intent(UnaryActivity.this, UnaryPublishActivity.class);
                startActivity(intent);
                break;
            case R.id.unary_begin:
                if (CommonUtils.IsLogin(UnaryActivity.this)) {
                    goodsShoppingCartListBean.clear();
                    if (tag != null && tag instanceof Integer) {
                        final int position = (Integer) tag;
                        UnaryListBean.ListResultBean listResultBean = unaryListBean.getListResult().get(position);
                        ShopCarBean.ResultBean.GoodsShoppingCartListBean goodsShoppingCartListBean2 = new ShopCarBean.ResultBean.GoodsShoppingCartListBean(
                                listResultBean.getGoodsId(), listResultBean.getGoodsColorId(),
                                listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                                listResultBean.getGoodsStandId(), listResultBean.getColorName() == null ? "0" : listResultBean.getColorName().toString(),
                                1, 0, listResultBean.getGoodsName(), listResultBean.getGoodsLogo().toString(), 1
                        );
                        goodsShoppingCartListBean.add(goodsShoppingCartListBean2);
                        Intent intent1 = new Intent(UnaryActivity.this, MakeOrderActivity.class);
                        intent1.putExtra("allmoney", 1);
                        intent1.putExtra("data", goodsShoppingCartListBean);
                        intent1.putExtra("payfrom", "4"); //代表从一元购结算的
                        intent1.putExtra("OnePurchaseId", listResultBean.getId()); //代表从支付的场次
                        startActivity(intent1);
                    }
                } else {
                    Intent intent2 = new Intent(UnaryActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    Toast.makeText(UnaryActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_unary_back:
                finish();
                break;
            case R.id.tv_unary_introduce:
                Intent intent1 = new Intent(UnaryActivity.this, UnaryIntroduceActivity.class);
                startActivity(intent1);
                break;
        }

    }

    /**
     * @param status   四种状态：1代表正在开抢 2等待开奖 3已开奖 0 暂未开始
     * @param page     页数，默认是每页20条数据
     * @param sort     排序根据
     * @param sorttype 排序方式 desc或者asc
     */
    private void GetUnaryListBean(final String status, final int page, final String sort, final String sorttype) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    if (unaryListBean.getListResult().size() == 0) {
                        Toast.makeText(mcontext, "暂无数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<UnaryListBean.ListResultBean> newListResult = unaryListBean.getListResult();
                    if (page == 1) {
                        listResult = newListResult;
                        unary_recyclerView.setNestedScrollingEnabled(false);
                        adapter = new UnaryAdapter(listResult, UnaryActivity.this);
                        unary_recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(UnaryActivity.this);
                        adapter.setOnBeginClickListener(UnaryActivity.this);
                    } else {
                        if (listResult == null) {
                            listResult = newListResult;
                            unary_recyclerView.setNestedScrollingEnabled(false);
                            adapter = new UnaryAdapter(listResult, UnaryActivity.this);
                            unary_recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(UnaryActivity.this);
                            adapter.setOnBeginClickListener(UnaryActivity.this);
                        } else {
                            listResult.addAll(newListResult);
                            adapter.notifyDataSetChanged();
                            adapter.setOnItemClickListener(UnaryActivity.this);
                        }
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String result = new String(error.networkResponse.data);
                Toast.makeText(mcontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //最先开奖的放在前面
                Map<String, String> map = new HashMap<>();
                map.put("State", status);
                map.put("CurrentPage", String.valueOf(page));
                map.put("PageSize", "20");
                map.put("SortValue", sort);
                map.put("SortType", sorttype);
                map.put("TopCount", "0");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    /**
     * 初始化一元购 默认是选择人气
     */
    private void InitUnaryListBean() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/OnepurchaseApi/ReceiveOnePurchaseData", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                unaryListBean = gson.fromJson(response, UnaryListBean.class);
                if (unaryListBean.isIsSuccess()) {
                    if (unaryListBean.getListResult().size() == 0) {
                        Toast.makeText(mcontext, "暂无数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listResult = unaryListBean.getListResult();
                    FullyGridLayoutManager manager = new FullyGridLayoutManager(UnaryActivity.this, 2);
                    manager.setOrientation(GridLayoutManager.VERTICAL);
                    manager.setSmoothScrollbarEnabled(true);
                    unary_recyclerView.setLayoutManager(manager);
                    adapter = new UnaryAdapter(listResult, UnaryActivity.this);
                    unary_recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(UnaryActivity.this);
                    adapter.setOnBeginClickListener(UnaryActivity.this);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                String result = new String(error.networkResponse.data);
                Toast.makeText(mcontext, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //最先开奖的放在前面
                Map<String, String> map = new HashMap<>();
                map.put("State", "1");
                map.put("Current", "1");
                map.put("PageSize", "200");
                map.put("SortValue", "joinCount");
                map.put("SortType", "desc");
                map.put("TopCount", "0");
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CleanTxtView() {
        webview_introduce.setVisibility(View.GONE);
        lgiv_unary_introduce.setVisibility(View.GONE);
        unary_recyclerView.setVisibility(View.VISIBLE);
        unary_popularity.setTextColor(Color.parseColor("#737373"));
        unary_new.setTextColor(Color.parseColor("#737373"));
        unary_introduce.setTextColor(Color.parseColor("#737373"));
    }

    // threeimage 点击事件
    @Override
    public void onItemClick(View view, Object postion) {
        int postionIndex = Integer.parseInt(String.valueOf(postion));
        if (threeImageListResult.size() >= (postionIndex + 1)) {
            UnaryListBean.ListResultBean listResultBean = threeImageListResult.get(postionIndex);
            Intent intent = new Intent(UnaryActivity.this, UnaryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", listResultBean);
            intent.putExtra("type",1); //代表从 最新揭晓跳转的
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBannerImage();
        initThreeImageUrl();
        InitUnaryListBean();
        initWebview();
    }
}
