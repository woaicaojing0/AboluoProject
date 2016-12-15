package com.aboluo.com;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.XUtils.ScreenUtils;
import com.aboluo.adapter.EvaluationPicAdapter;
import com.aboluo.adapter.GridViewAdapter;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by cj34920 on 2016/12/14.
 */

public class EvaluationActivity extends TakePhotoActivity implements View.OnClickListener {
    private LinearLayout evaluation_take_photo, btn_beginEvaluation;
    private ArrayList<ImageView> imageViews;  //显示的imageview
    private ArrayList<String> imageViewsurl; //未压缩的地址
    private CompressConfig compressConfig;
    private GridView take_pic_gridview;
    private EvaluationPicAdapter evaluationPicAdapter;
    private ArrayList<TImage> tImageArrayList;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;
    private int num = 0;
    private QiNiuToken qiNiuToken;
    private String GoodsImageurl;
    private int Orderid;
    private int Goodsid;
    private ImageView evaluation_goodsimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_evaluation);
        init();
        evaluation_take_photo.setOnClickListener(this);
        int width = (ScreenUtils.getScreenWidth(this) - CommonUtils.dip2px(this, 12)) / 4;
        evaluation_take_photo.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        take_pic_gridview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width * 2));
        take_pic_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EvaluationActivity.this, EvaluationDetailImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imgeurl", tImageArrayList.get(position).getOriginalPath());
                bundle.putInt("item", position);
                intent.putExtras(bundle);
                String transitionName = "images";
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(EvaluationActivity.this, view, transitionName);
                startActivityForResult(intent, 1, activityOptionsCompat.toBundle());
            }
        });
        btn_beginEvaluation.setOnClickListener(this);
    }

    private void GetLastActivityInfo() {
        Bundle bundle = getIntent().getExtras();
        GoodsImageurl = bundle.getString("Imageurl", "");
        Orderid = bundle.getInt("Orderid", 0);
        Goodsid = bundle.getInt("Goodsid", 0);
    }

    private void init() {
        GetLastActivityInfo();
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
        evaluation_take_photo = (LinearLayout) findViewById(R.id.evaluation_take_photo);
        evaluation_goodsimage = (ImageView) findViewById(R.id.evaluation_goodsimage);
        btn_beginEvaluation = (LinearLayout) findViewById(R.id.btn_beginEvaluation);
        take_pic_gridview = (GridView) findViewById(R.id.take_pic_gridview);
        imageViews = new ArrayList<>();
        imageViewsurl = new ArrayList<>();
        tImageArrayList = new ArrayList<>();
        compressConfig = new CompressConfig.Builder().setMaxSize(10 * 1024).setMaxPixel(150).create();
        GetQiNiuToken();
        picasso.load(ImageUrl + GoodsImageurl).placeholder(R.drawable.imagviewloading)
                .error(R.drawable.imageview_error).into(evaluation_goodsimage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluation_take_photo:
//                View contentView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
//                CustomHelper customHelper = CustomHelper.of(contentView);
                //CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setApectY(1).setWithOwnCrop(true).create();
                int size = 6 - tImageArrayList.size();
                if (size > 0) {
                    getTakePhoto().onEnableCompress(compressConfig, true);
                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder().setWithOwnGallery(true);
                    getTakePhoto().setTakePhotoOptions(builder.create());
                    getTakePhoto().onPickMultiple(size);
                } else {
                    Toast.makeText(this, "最多只能选择6张", Toast.LENGTH_SHORT).show();
                }
//                getTakePhoto().onPickFromDocuments();
                //customHelper.onClick(v,getTakePhoto());
                break;
            case R.id.btn_beginEvaluation:
                String token = qiNiuToken.getFileUploadToken();
                UploadImage(imageViewsurl, token);
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ImageView imageView = new ImageView(this);
        picasso.load(new File(result.getImages().get(0).getOriginalPath())).into(imageView);
        // store_images.addView(imageView);
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
        if (evaluationPicAdapter == null) {
            evaluationPicAdapter = new EvaluationPicAdapter(this, imageViewsurl);
            take_pic_gridview.setAdapter(evaluationPicAdapter);
        } else {
            evaluationPicAdapter.notifyDataSetChanged();
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
                        if (evaluationPicAdapter == null) {
                            evaluationPicAdapter = new EvaluationPicAdapter(this, imageViewsurl);
                            take_pic_gridview.setAdapter(evaluationPicAdapter);
                        } else {
                            evaluationPicAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * @param filepath 文件的地址
     */
    private void UploadImage(final ArrayList<String> filepath, String token) {
        num = 0;
        //pdialog.setTitleText("上传中......");
        Configuration config = new Configuration.Builder().zone(Zone.zone0).build();
//  重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        for (int i = 0; i < filepath.size(); i++) {
            String key = "evaluateImage/android/" + UUID.randomUUID().toString();
            uploadManager.put(filepath.get(i).toString(), key, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                            if (info.isOK()) {
                                num++;
                                if (num == filepath.size()) {
                                    pdialog.dismiss();
                                    Toast.makeText(EvaluationActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                                    //UploadEvaluation();
                                } else {
                                }

                            }
                        }
                    }, null);
        }

    }

    private void UploadEvaluation() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("\\", "");
                response = response.substring(1, response.length() - 1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MemberId", "1975");
                map.put("OrderId", "508");
                map.put("ExpressId", "1");
                map.put("APPToken", APPToken);
                return map;
            }

            ;
        };
        requestQueue.add(stringRequest);
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
                Toast.makeText(EvaluationActivity.this, "Token" + s, Toast.LENGTH_SHORT).show();
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
}