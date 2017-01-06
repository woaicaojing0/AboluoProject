package com.aboluo.com;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.adapter.RefundGridViewAdapter;
import com.aboluo.model.BaseModel;
import com.aboluo.model.OrderDetailInfo;
import com.aboluo.model.QiNiuToken;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 申请售后界面
 * Created by CJ on 2016/12/11.
 */

public class RefundDetailActivity extends TakePhotoActivity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private GridView refund_gridview;
    private ArrayList<TImage> tImageArrayList; //保存图片的所有信息
    private ArrayList<String> imageViewsurl; //未压缩的地址
    private RefundGridViewAdapter refundGridViewAdapter;
    private LinearLayout refund_take_photo, begin_refund;
    private CompressConfig compressConfig;
    private OrderDetailInfo.ResultBean.OrderItemListBean orderItemListBean;
    private TextView refundetail_time, refund_price, refunddetail_number;
    private EditText edit_refundreson;
    private String OrderCode;
    private int num = 0;
    private ArrayList<String> ImageUploadNameList; //图片的上传保存的文件名
    private QiNiuToken qiNiuToken;
    private RadioGroup tuikuan_type;
    private RadioButton goodsAndmoney, only_huanhuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refunddetail);
        init();
        refund_take_photo.setOnClickListener(this);
        begin_refund.setOnClickListener(this);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        refundetail_time.setText(date);
        if (orderItemListBean == null) {
        } else {
            refund_price.setText(String.valueOf(orderItemListBean.getGoodsPrice()));
            refunddetail_number.setText(OrderCode);
        }
        tuikuan_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (goodsAndmoney.getId() == checkedId) {
                    refund_price.setText(String.valueOf(orderItemListBean.getGoodsPrice()));
                } else if (only_huanhuo.getId() == checkedId) {
                    refund_price.setText("0");
                }
            }
        });
        only_huanhuo.setChecked(true);
    }

    private void init() {
        compressConfig = new CompressConfig.Builder().setMaxSize(10 * 1024).setMaxPixel(150).create();
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
        refund_gridview = (GridView) findViewById(R.id.refund_gridview);
        refund_take_photo = (LinearLayout) findViewById(R.id.refund_take_photo);
        begin_refund = (LinearLayout) findViewById(R.id.begin_refund);
        refundetail_time = (TextView) findViewById(R.id.refundetail_time);
        refund_price = (TextView) findViewById(R.id.refund_price);
        refunddetail_number = (TextView) findViewById(R.id.refunddetail_number);
        edit_refundreson = (EditText) findViewById(R.id.edit_refundreson);
        tuikuan_type = (RadioGroup) findViewById(R.id.tuikuan_type);
        goodsAndmoney = (RadioButton) findViewById(R.id.goodsAndmoney);
        only_huanhuo = (RadioButton) findViewById(R.id.only_huanhuo);
        imageViewsurl = new ArrayList<>();
        tImageArrayList = new ArrayList<>();
        ImageUploadNameList = new ArrayList<>();
        orderItemListBean = getIntent().getExtras().getParcelable("refundinfo");
        OrderCode = getIntent().getExtras().getString("OrderCode", "0");
        GetQiNiuToken();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (tImageArrayList.size() == 0) {
            for (int i = 0; i < result.getImages().size(); i++) {
                tImageArrayList.add(result.getImages().get(i));
            }
        } else {
            for (int i = 0; i < result.getImages().size(); i++) {
                Iterator<TImage> iterator = tImageArrayList.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getCompressPath().equals(result.getImages().get(i).getCompressPath())) {
                        iterator.remove();
                    } else {
                    }
                }
                tImageArrayList.add(result.getImages().get(i));
            }
        }
        imageViewsurl.clear();
        for (int i = 0; i < tImageArrayList.size(); i++) {
            imageViewsurl.add(tImageArrayList.get(i).getCompressPath());
        }
        if (refundGridViewAdapter == null) {
            refundGridViewAdapter = new RefundGridViewAdapter(this, imageViewsurl);
            refund_gridview.setAdapter(refundGridViewAdapter);
        } else {
            refundGridViewAdapter.notifyDataSetChanged();
        }
        refundGridViewAdapter.setDeleteOnclickListener(this);
        refundGridViewAdapter.setShowOnclickListener(this);
    }


    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int key = v.getId();
        switch (v.getId()) {
            case R.id.refund_take_photo:
                int size = 3 - tImageArrayList.size();
                if (size > 0) {
                    getTakePhoto().onEnableCompress(compressConfig, true);
                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder().setWithOwnGallery(true);
                    getTakePhoto().setTakePhotoOptions(builder.create());
                    getTakePhoto().onPickMultiple(size);
                } else {
                    Toast.makeText(this, "最多只能选择3张", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.refund_img_delete:
                if (tag != null && tag instanceof Integer) {
                    Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
                    int item = (int) tag;
                    tImageArrayList.remove(item);
                    imageViewsurl.clear();
                    for (int i = 0; i < tImageArrayList.size(); i++) {
                        imageViewsurl.add(tImageArrayList.get(i).getCompressPath());
                    }
                    if (refundGridViewAdapter == null) {
                        refundGridViewAdapter = new RefundGridViewAdapter(this, imageViewsurl);
                        refund_gridview.setAdapter(refundGridViewAdapter);
                    } else {
                        refundGridViewAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.refund_img_show:
                if (tag != null && tag instanceof Integer) {
                    Intent intent = new Intent(RefundDetailActivity.this, EvaluationDetailImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imgeurl", tImageArrayList.get((Integer) tag).getOriginalPath());
                    bundle.putInt("item", (Integer) tag);
                    intent.putExtras(bundle);
                    String transitionName = "images";
                    ActivityOptionsCompat activityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(RefundDetailActivity.this, v, transitionName);
                    startActivityForResult(intent, 1, activityOptionsCompat.toBundle());
                }
                break;
            case R.id.begin_refund:
                if (edit_refundreson.getText().length() == 0) {
                    Toast.makeText(this, "请先填写退款原因", Toast.LENGTH_SHORT).show();
                } else {

                    if (imageViewsurl.size() == 0) {
                        Toast.makeText(this, "请先上传图片", Toast.LENGTH_SHORT).show();
                    } else {
                        String token = qiNiuToken.getFileUploadToken();
                        UploadImage(imageViewsurl, token);
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                } else {
                    int itemid = bundle.getInt("item", 8);
                    if (itemid == 8) {
                    } else {
                        tImageArrayList.remove(itemid);
                        imageViewsurl.clear();
                        for (int i = 0; i < tImageArrayList.size(); i++) {
                            imageViewsurl.add(tImageArrayList.get(i).getCompressPath());
                        }
                        if (refundGridViewAdapter == null) {
                            refundGridViewAdapter = new RefundGridViewAdapter(this, imageViewsurl);
                            refund_gridview.setAdapter(refundGridViewAdapter);
                        } else {
                            refundGridViewAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void GetQiNiuToken() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "/api/ActiveApi/ReciveQiNiuToken", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                qiNiuToken = gson.fromJson(response, QiNiuToken.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                byte[] bytecode = error.networkResponse.data;
                String s = new String(bytecode);
                Toast.makeText(RefundDetailActivity.this, "Token" + s, Toast.LENGTH_SHORT).show();
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

    /**
     * @param filepath 文件的地址
     */
    private void UploadImage(final ArrayList<String> filepath, String token) {
        pdialog.show();
        num = 0;
        ImageUploadNameList.clear();
        //pdialog.setTitleText("上传中......");
        Configuration config = new Configuration.Builder().zone(Zone.zone0).build();
//  重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        for (int i = 0; i < filepath.size(); i++) {
            String key = "refundImage/android/" + UUID.randomUUID().toString();
            final int finalI = i;
            uploadManager.put(filepath.get(i).toString(), key, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                            if (info.isOK()) {
                                ImageUploadNameList.add(key);
                                num++;
                                if (num == filepath.size()) {
                                    Toast.makeText(RefundDetailActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                                    UploadRefund();
                                } else {
                                    if(finalI +1==filepath.size()) {
                                        Toast.makeText(RefundDetailActivity.this, "图片上传失败，请重试", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        }
                    }, null);
        }

    }

    private void UploadRefund() {
        final String refundImage = CommonUtils.listToString(ImageUploadNameList, ';');
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "/api/Order/ReceiveAddOrUpdateOrderRefund", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pdialog.dismiss();
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
                BaseModel baseModel = gson.fromJson(response, BaseModel.class);
                Toast.makeText(RefundDetailActivity.this, baseModel.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdialog.dismiss();
                byte[] ErrorCode = error.networkResponse.data;
                String result = new String(ErrorCode);
                Log.i("EvaluationUpload", result);
                Toast.makeText(RefundDetailActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", String.valueOf(orderItemListBean.getOrderId()));
                map.put("orderItemId", String.valueOf(orderItemListBean.getOrderItemId()));
                map.put("goodsId", String.valueOf(orderItemListBean.getGoodsId()));
                map.put("goodsName", String.valueOf(orderItemListBean.getGoodsName()));
                map.put("refundReson", edit_refundreson.getText().toString());
                map.put("refundImage", refundImage);
                map.put("MemberId", MemberId);
                map.put("APPToken", APPToken);
                //申请售后类型（0 退货 1 退款）
                if (only_huanhuo.isChecked()) {
                    map.put("orderRefundType", "0");
                } else {
                    map.put("orderRefundType", "1");
                }
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
    }


}
