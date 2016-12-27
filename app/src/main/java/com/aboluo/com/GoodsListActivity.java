package com.aboluo.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.RBCallbkRecyclerView;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.RecycleViewAdapter;
import com.aboluo.model.BrandBean;
import com.aboluo.model.GoodsListInfo;
import com.aboluo.widget.MyRadioGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/10/18.
 * 这是商品列表界面
 */

public class GoodsListActivity extends Activity implements RecycleViewAdapter.OnRecyclerViewItemClickListener, View.OnClickListener {
    private RequestQueue requestQueue;
    private StringRequest requestlist;
    //显示的容器，用于替代listview
    private RBCallbkRecyclerView mRBCallbkRecyclerView;
    //接口地址
    private String url;
    private static String APPToken;
    //容器适配器
    private RecycleViewAdapter recycleViewAdapter;
    //保存商品列表信息
    private GoodsListInfo listBean;
    //显示进度条
    private SweetAlertDialog pdialog;
    //保存从其他页面跳转过来的时候goods_type的id
    private int goods_type_id;
    //商品类别的名称
    private String goods_type_name;
    //商品名称和返回按钮
    private TextView goods_list_typeName;
    private ImageView goods_list_back;
    //搜索框
    private EditText goods_list_search;
    //切换布局按钮，筛选按钮，筛选中的重置，筛选中的确定
    private Button buju, btn_filtrate, btn_goodslist_rest, btn_goodslist_surefiltrate, goods_detail_price,btn_default;
    //筛选采用的draverlayout布局
    private DrawerLayout drawer_layout;
    //屏幕的宽度，用来设置筛选界面的宽度
    private int screenwith, brandWith;
    //筛选界面的布局
    private RelativeLayout right_shaixuan;
    private BrandBean brandBean;   //商品品牌属性
    private MyRadioGroup goods_list_brand_radiogroup;
    private int currentpages = 1;
    private int pagesize = 10;
    private List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean;
    private static String GoodsName;
    private static int GoodsBrandId;
    private static boolean IsPriceSort;
    private static boolean LineType =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodlist);
        init();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        initdate(1);
        Intent intent = getIntent();
        goods_type_id = intent.getIntExtra("goods_type_id", -1);
        GoodsBrandId = intent.getIntExtra("GoodsBrandId", -1);
        goods_type_name = intent.getStringExtra("goods_type_name");
        GoodsName = intent.getStringExtra("GoodsName");
        if (GoodsName == null) {
            GoodsName = "";
        }
        if (goods_type_name == null) {
        } else {
            goods_list_typeName.setText(goods_type_name);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRBCallbkRecyclerView.setLayoutManager(linearLayoutManager);
        goods_list_back.setOnClickListener(this);
        pdialog.show();
        screenwith = ScreenUtils.getScreenWidth(this);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动
        brandWith = (screenwith / 10) * 9;
        right_shaixuan.setLayoutParams(new RelativeLayout.LayoutParams(brandWith, ViewGroup.LayoutParams.MATCH_PARENT));
        goods_list_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    goods_list_search.setGravity(Gravity.CENTER_VERTICAL);
                    goods_list_search.setPadding(16,0,0,0);
                } else {
                    goods_list_search.setGravity(Gravity.CENTER);
                }
            }
        });
        goods_list_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(goods_list_back.getWindowToken(), 0);
                    //Toast.makeText(GoodsListActivity.this, "wewe", Toast.LENGTH_SHORT).show();
                    GoodsName = goods_list_search.getText().toString();
                    initdate(1);
                    return true;
                } else {
                    return false;
                }
            }
        });
        btn_filtrate.setOnClickListener(this);
        buju.setOnClickListener(this);
        btn_goodslist_rest.setOnClickListener(this);
        btn_goodslist_surefiltrate.setOnClickListener(this);
        initfiltrate();
        mRBCallbkRecyclerView.setReachBottomRow(4);
        mRBCallbkRecyclerView.setOnReachBottomListener(new RBCallbkRecyclerView.OnReachBottomListener() {
            @Override
            public void onReachBottom() {
                //即将到达几部，进行加载更多操作
                currentpages++;
                initdate(currentpages);
            }
        });
        goods_detail_price.setOnClickListener(this);
        btn_default.setOnClickListener(this);
    }

    private void init() {
        goods_list_typeName = (TextView) findViewById(R.id.goods_list_typeName);
        goods_list_back = (ImageView) findViewById(R.id.goods_list_back);
        goods_list_search = (EditText) findViewById(R.id.goods_list_search);
        buju = (Button) findViewById(R.id.buju);
        btn_filtrate = (Button) findViewById(R.id.btn_filtrate);
        btn_goodslist_rest = (Button) findViewById(R.id.btn_goodslist_rest);
        btn_default = (Button) findViewById(R.id.btn_default);
        btn_goodslist_surefiltrate = (Button) findViewById(R.id.btn_goodslist_surefiltrate);
        goods_detail_price = (Button) findViewById(R.id.goods_detail_price);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRBCallbkRecyclerView = (RBCallbkRecyclerView) findViewById(R.id.goods_list_recycleview);
        right_shaixuan = (RelativeLayout) findViewById(R.id.right_shaixuan);
        goods_list_brand_radiogroup = (MyRadioGroup) findViewById(R.id.goods_list_brand_radiogroup);
        requestQueue = MyApplication.getRequestQueue();
        url = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");

    }

    private void initdate(final int currentpage) {
        requestlist = new StringRequest(Request.Method.POST, url + "/api/Goods/ReceiveGoodsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Gson gson = new Gson();
                Log.i("woaicaojing", url + "/api/Goods/ReceiveGoodsList");
                Log.i("woaicaojing", response);
                listBean = gson.fromJson(response, GoodsListInfo.class);
                if (currentpage == 1) {
                    goodsListBean = listBean.getResult().getGoodsList();
                    if(!LineType)
                    {
                        //2列垂直布局
                        mRBCallbkRecyclerView.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));//设置RecyclerView布局管理器为2列垂直排布
                        recycleViewAdapter = new RecycleViewAdapter(goodsListBean, GoodsListActivity.this, 1);
                    }else
                    {
                        //线性布局水平布局
                        mRBCallbkRecyclerView.setLayoutManager(new LinearLayoutManager(GoodsListActivity.this));
                        recycleViewAdapter = new RecycleViewAdapter(goodsListBean, GoodsListActivity.this, 0);
                    }
                    mRBCallbkRecyclerView.setAdapter(recycleViewAdapter);
                    recycleViewAdapter.setOnItemClickListener(GoodsListActivity.this);
                } else {
                    if (goodsListBean == null) {
                        goodsListBean = listBean.getResult().getGoodsList();
                        recycleViewAdapter = new RecycleViewAdapter(goodsListBean, GoodsListActivity.this, 0);
                        mRBCallbkRecyclerView.setAdapter(recycleViewAdapter);
                        recycleViewAdapter.setOnItemClickListener(GoodsListActivity.this);

                    } else {
                        List<GoodsListInfo.ResultBean.GoodsListBean> goodsListBean2 = listBean.getResult().getGoodsList();
                        goodsListBean.addAll(goodsListBean2);
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                }
                pdialog.dismiss();

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
                if (GoodsName.length() == 0) { //搜索框搜素
                } else {
                    map.put("GoodsName", GoodsName);
                }
                if (goods_type_id == -1) //商品的类别
                {
                } else {
                    map.put(" ", String.valueOf(goods_type_id));
                }
                if (GoodsBrandId == -1) //商品的品牌
                {
                } else {
                    map.put("GoodsBrandId", String.valueOf(GoodsBrandId));
                }
                map.put("APPToken", APPToken);
                map.put("IsPriceSort", String.valueOf(IsPriceSort));
                map.put("CurrentPage", String.valueOf(currentpage));
                map.put("PageSize", String.valueOf(pagesize));
                return map;
            }
        };
        requestQueue.add(requestlist);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        int goods_id = goodsListBean.get(position).getGoodsId();
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra("goods_id", goods_id);
        String transitionName = "detail";
        ActivityOptionsCompat transitionActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsListActivity.this, view.findViewById(R.id.goods_image), transitionName);
        startActivity(intent, transitionActivityOptionsCompat.toBundle());
//        startActivity(intent);
    }

    /**
     * 根据传入的商品的类别的id获取品牌的信息
     */
    private void initfiltrate() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/api/GoodsType/ReceiveGoodsBrandListByGoodsTypeId", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                Log.i("woaicaojingpingpai", response);
                Gson gson = new Gson();
                brandBean = gson.fromJson(response, BrandBean.class);
                if (brandBean.getResult() == null) {
                } else if (brandBean.getResult().getGoodsBrandList() == null) {
                } else {
                    List<BrandBean.ResultBean.GoodsBrandListBean> listBrand = brandBean.getResult().getGoodsBrandList();
                    int size = listBrand.size();
                    int hang = size / 3;
                    int yushu = size % 3;
                    if (hang == 0) {
                        LinearLayout linearLayout = new LinearLayout(GoodsListActivity.this);
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsListActivity.this, 44)));
                        for (int i = 0; i < listBrand.size(); i++) {
                            final RadioButton button = new RadioButton(GoodsListActivity.this);
                            int buttonwith = (brandWith - CommonUtils.dip2px(GoodsListActivity.this, 56));
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(buttonwith / 3, CommonUtils.dip2px(GoodsListActivity.this, 40));
                            layoutParams.setMargins(CommonUtils.dip2px(GoodsListActivity.this, 10), 0, 0, 0);
                            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
                            Bitmap a = null;
                            button.setButtonDrawable(new BitmapDrawable(a));
                            button.setGravity(Gravity.CENTER);
                            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
                            button.setTextColor(csl);
                            button.setLayoutParams(layoutParams);
                            button.setText(listBrand.get(i).getBrandName());
                            button.setId(i);
                            button.setTag(listBrand.get(i).getId());
                            final int finalI = i;
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Object tag = v.getTag();
                                    if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                                        GoodsBrandId = (Integer) tag;
                                        //Toast.makeText(GoodsListActivity.this, GoodsBrandId + "", Toast.LENGTH_SHORT).show();
                                        initdate(1);
                                        drawer_layout.closeDrawers();
                                    }
                                }
                            });
                            linearLayout.addView(button);
                        }
                        goods_list_brand_radiogroup.addView(linearLayout);
                    } else {
                        if (yushu == 0) {
                            for (int i = 0; i < hang; i++) {
                                creatBrandRadioButton(i, 3, listBrand);
                            }
                        } else {
                            hang = hang + 1;
                            for (int i = 0; i < hang; i++) {
                                if (i == (hang - 1)) {
                                    creatBrandRadioButton(i, yushu, listBrand);
                                } else {
                                    creatBrandRadioButton(i, 3, listBrand);
                                }
                            }
                        }
                    }

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
                if (goods_type_id == -1) {
                    map.put("GoodsTypeId", "1");
                } else {
                    map.put("GoodsTypeId", String.valueOf(goods_type_id));
                }
                map.put("APPToken", APPToken);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void creatBrandRadioButton(int i, int num, List<BrandBean.ResultBean.GoodsBrandListBean> listBrand) {
        LinearLayout linearLayout = new LinearLayout(GoodsListActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(GoodsListActivity.this, 44)));
        for (int i2 = 0; i2 < num; i2++) {
            final RadioButton button = new RadioButton(GoodsListActivity.this);
            int buttonwith = (brandWith - CommonUtils.dip2px(GoodsListActivity.this, 80));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(buttonwith / 3, CommonUtils.dip2px(GoodsListActivity.this, 40));
            layoutParams.setMargins(CommonUtils.dip2px(GoodsListActivity.this, 10), 0, 0, 0);
            button.setBackground(getResources().getDrawable(R.drawable.rdobtn_selecter));
            Bitmap a = null;
            button.setButtonDrawable(new BitmapDrawable(a));
            button.setGravity(Gravity.CENTER);
            ColorStateList csl = getResources().getColorStateList(R.color.radio_text_selector);
            button.setTextColor(csl);
            button.setLayoutParams(layoutParams);
            button.setText(listBrand.get(i * 3 + i2).getBrandName().trim());
            button.setId(i * 3 + i2);
            button.setTag(listBrand.get(i * 3 + i2).getId());
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(GoodsListActivity.this, button.getText().toString(), Toast.LENGTH_SHORT).show();
                    Object tag = v.getTag();
                    if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
                        GoodsBrandId = (Integer) tag;
                        //Toast.makeText(GoodsListActivity.this, GoodsBrandId + "", Toast.LENGTH_SHORT).show();
                        initdate(1);
                        drawer_layout.closeDrawers();
                    }
                }
            });
            linearLayout.addView(button);
        }
        goods_list_brand_radiogroup.addView(linearLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filtrate:
                drawer_layout.openDrawer(Gravity.RIGHT);
                break;
            case R.id.buju:
                LineType = !LineType;
                if (v.getTag() == null) {
                    mRBCallbkRecyclerView.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));//设置RecyclerView布局管理器为2列垂直排布
                    recycleViewAdapter = new RecycleViewAdapter(goodsListBean, GoodsListActivity.this, 1);
                    int i = recycleViewAdapter.getItemCount();
                    mRBCallbkRecyclerView.setAdapter(recycleViewAdapter);
                    recycleViewAdapter.setOnItemClickListener(GoodsListActivity.this);
                    v.setTag("wangge");
                    mRBCallbkRecyclerView.smoothScrollToPosition(i);
                } else {
                    mRBCallbkRecyclerView.setLayoutManager(new LinearLayoutManager(GoodsListActivity.this));
                    recycleViewAdapter = new RecycleViewAdapter(goodsListBean, GoodsListActivity.this, 0);
                    mRBCallbkRecyclerView.setAdapter(recycleViewAdapter);
                    recycleViewAdapter.setOnItemClickListener(GoodsListActivity.this);
                    v.setTag(null);
                    int i = recycleViewAdapter.getItemCount();
                    mRBCallbkRecyclerView.smoothScrollToPosition(i);
                }
                break;
            case R.id.goods_list_back:
                finish();
                break;
            case R.id.btn_goodslist_rest:
                goods_list_brand_radiogroup.clearCheck();
                GoodsBrandId = -1;
                initdate(1);
                break;
            case R.id.btn_goodslist_surefiltrate:
                drawer_layout.closeDrawers();
                break;
            case R.id.goods_detail_price:
                IsPriceSort = !IsPriceSort;
                initdate(1);
                break;
            case R.id.btn_default:
                IsPriceSort = false;
                LineType= true;
                initdate(1);
                break;
        }
    }
}
