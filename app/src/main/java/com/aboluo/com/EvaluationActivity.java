package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImage;
import com.jph.takephoto.compress.CompressImageImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cj34920 on 2016/12/14.
 */

public class EvaluationActivity extends TakePhotoActivity implements View.OnClickListener {
    private LinearLayout evaluation_take_photo, store_images,store_images2;
    private ArrayList<ImageView> imageViews;
    private ArrayList<String> imageViewsurl;
    private Picasso picasso;
    private CompressConfig compressConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_evaluation);
        init();
        evaluation_take_photo.setOnClickListener(this);
    }

    private void init() {
        evaluation_take_photo = (LinearLayout) findViewById(R.id.evaluation_take_photo);
        store_images2 = (LinearLayout) findViewById(R.id.store_images2);
        store_images = (LinearLayout) findViewById(R.id.store_images);
        imageViews = new ArrayList<>();
        imageViewsurl = new ArrayList<>();
        picasso = Picasso.with(this);
        compressConfig = new CompressConfig.Builder().setMaxSize(10 * 1024).setMaxPixel(150).create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluation_take_photo:
//                View contentView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
//                CustomHelper customHelper = CustomHelper.of(contentView);
                //CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setApectY(1).setWithOwnCrop(true).create();
                int size = 6 - imageViews.size();
                if(size>0) {
                    getTakePhoto().onEnableCompress(compressConfig, true);
                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder().setWithOwnGallery(true);
                    getTakePhoto().setTakePhotoOptions(builder.create());
                    getTakePhoto().onPickMultiple(6 - imageViews.size());
                }else {
                    Toast.makeText(this, "最多只能选择6张", Toast.LENGTH_SHORT).show();
                }
//                getTakePhoto().onPickFromDocuments();
                //customHelper.onClick(v,getTakePhoto());
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        InitStoreImages(result.getImages());

    }

    private void InitStoreImages(List<TImage> list) {
        if (store_images.getChildCount() >= 0 && store_images.getChildCount() < 4) {
            int width = ScreenUtils.getScreenWidth(this) / 4;
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(this);
                imageViewsurl.add(list.get(i).getOriginalPath());
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EvaluationActivity.this, EvaluationDetailImageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imgeurl", imageViewsurl.get(finalI));
                        intent.putExtras(bundle);
                        String transitionName = "images";
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EvaluationActivity.this, v, transitionName);
                        startActivityForResult(intent, 1, activityOptionsCompat.toBundle());
                    }
                });
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(CommonUtils.dip2px(this, 80), CommonUtils.dip2px(this, 80)));
                picasso.load(new File(list.get(i).getOriginalPath())).into(imageView);
                imageViews.add(imageView);
                store_images.addView(imageView);
            }
        }
        else {
            int width = ScreenUtils.getScreenWidth(this) / 4;
            for (int i = 0; i < list.size(); i++) {
                ImageView imageView = new ImageView(this);
                imageViewsurl.add(list.get(i).getOriginalPath());
                final int finalI = i + 4;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(EvaluationActivity.this, EvaluationDetailImageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imgeurl", imageViewsurl.get(finalI));
                        intent.putExtras(bundle);
                        String transitionName = "images";
                        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(EvaluationActivity.this, v, transitionName);
                        startActivityForResult(intent, 1, activityOptionsCompat.toBundle());
                    }
                });
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(CommonUtils.dip2px(this, 80), CommonUtils.dip2px(this, 80)));
                picasso.load(new File(list.get(i).getOriginalPath())).into(imageView);
                imageViews.add(imageView);
                store_images2.addView(imageView);
            }
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
                    String url = bundle.getString("imgeurl");
                    if (url == null) {
                    } else {
                        for (int i = 0; i < imageViewsurl.size(); i++) {
                            if (imageViewsurl.get(i).equals(url)) {
                                if(i<4) {
                                    store_images.removeView(imageViews.get(i));
                                }else {
                                    store_images2.removeView(imageViews.get(i));
                                }
                            } else {
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
