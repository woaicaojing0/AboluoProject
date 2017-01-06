package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.FavorAdapter;
import com.aboluo.model.BaseModel;
import com.aboluo.model.FavorBean;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;
import com.tandong.bottomview.view.BottomView;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by CJ on 2016/12/9.
 * 这是收藏页面
 */

public class FavorActivity extends Activity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private PullToRefreshListView favor_listview;
    private String MemberId;
    private FavorBean favorBean;
    private FavorAdapter favorAdapter;
    private BottomView bottomView;
    private LinearLayout un_favor, favor_share, favor_cancel;
    private ImageView unarydetail_text_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        init();
        favor_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FavorActivity.this, position + "item click", Toast.LENGTH_SHORT).show();
            }
        });
        unarydetail_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        favor_listview = (PullToRefreshListView) findViewById(R.id.favor_listview);
        unarydetail_text_back = (ImageView) findViewById(R.id.unarydetail_text_back);
        initData();
    }

    private void initData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/CollectApi/ReceiveGoodsCollectList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                favorBean = gson.fromJson(response, FavorBean.class);
                favorAdapter = new FavorAdapter(FavorActivity.this, favorBean.getResult(), ImageUrl);
                favorAdapter.setSetOnMoreItemClickListener(FavorActivity.this);
                favorAdapter.setSetOnShareItemClickListener(FavorActivity.this);
                favor_listview.setAdapter(favorAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                Log.i("woaicaojing", bytecode.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", MemberId);
                map.put("CurrentPage", "1");
                map.put("PageSize", "20");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (v.getId()) {
            case R.id.favor_item_more:
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
                    bottomView = new BottomView(this,
                            R.style.BottomViewTheme_Defalut, R.layout.favor_bottom);
                    bottomView.setAnimation(R.style.BottomToTopAnim);//设置动画，可选
                    un_favor = (LinearLayout) bottomView.getView().findViewById(R.id.un_favor);
                    favor_share = (LinearLayout) bottomView.getView().findViewById(R.id.favor_share);
                    favor_cancel = (LinearLayout) bottomView.getView().findViewById(R.id.favor_cancel);
                    un_favor.setOnClickListener(this);
                    favor_share.setOnClickListener(this);
                    favor_cancel.setOnClickListener(this);
                    un_favor.setTag(position);
                    favor_share.setTag(position);
                    bottomView.showBottomView(true);
                } else {
                }
                break;
            case R.id.favor_item_share: //sharesdk
//                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
//                    int position2 = (Integer) tag;
//                    beginShare(position2);
//                }
                break;
            case R.id.un_favor: //取消收藏按钮
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    final int id = favorBean.getResult().get(position).getCollectId();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/CollectApi/ReceiveDeleteGoodsCollect", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            response = response.replace("\\", "");
                            response = response.substring(1, response.length() - 1);
                            BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                            if (baseModel.isIsSuccess()) {
                                initData();
                                bottomView.dismissBottomView();
                                Toast.makeText(FavorActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FavorActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            byte[] bytescode = error.networkResponse.data;
                            Log.i("woaicaojing", bytescode.toString());
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("Ids", String.valueOf(id));
                            map.put("MemberId", MemberId);
                            map.put("APPToken", APPToken);
                            return map;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                }
                break;
            case R.id.favor_share: //返回按钮
                if (tag != null && tag instanceof Integer) { //解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的
                    int position = (Integer) tag;
                    beginShare(position);
                } else {
                }
                break;
            case R.id.favor_cancel: //最下面退出按钮
                bottomView.dismissBottomView();
                break;
        }
    }

    private void beginShare(int position2) {
        String detailurl0 = CommonUtils.GetValueByKey(FavorActivity.this, "backUrl")
                + "/moblie/Index?productId=" + favorBean.getResult().get(position2).getGoodsId();
        String imageurl = favorBean.getResult().get(position2).getGoodsLogo().toString();
        if (imageurl == null) {
        } else {
            String[] imageurls = imageurl.split(";");
            for (int i = 0; i < imageurls.length; i++) {
                imageurls[i] = ImageUrl + imageurls[i].toString();
            }
            Log.i("ShareSDKImageUrl", imageurls[0].toString());
            Log.i("ShareSDKURLDetail", detailurl0);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle("阿波罗分享");
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(detailurl0);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(favorBean.getResult().get(position2).getGoodsName().toString());
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath(imageurls[0].toString());//确保SDcard下面存在此张图片
            oks.setImageUrl(imageurls[0].toString());
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(detailurl0);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("这个商品不错哦");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(detailurl0);
            // 启动分享GUI
            oks.show(this);
        }
    }

    private void DeleteFavor(int id) {

    }
}
