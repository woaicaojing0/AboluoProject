package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.ScreenUtils;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/12/14.
 */

public class EvaluationActivity extends TakePhotoActivity implements View.OnClickListener {
    private LinearLayout evaluation_take_photo, store_images, store_images2, evaluation_take_photo2;
    private ArrayList<ImageView> imageViews;  //显示的imageview
    private ArrayList<String> imageViewsurl; //未压缩的地址
    private Picasso picasso;
    private CompressConfig compressConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_evaluation);
        init();
        evaluation_take_photo.setOnClickListener(this);
        evaluation_take_photo2.setOnClickListener(this);
        int width = (ScreenUtils.getScreenWidth(this)- CommonUtils.dip2px(this,12))/ 4;
        evaluation_take_photo.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        evaluation_take_photo2.setLayoutParams(new LinearLayout.LayoutParams(width, width));
    }

    private void init() {
        evaluation_take_photo = (LinearLayout) findViewById(R.id.evaluation_take_photo);
        evaluation_take_photo2 = (LinearLayout) findViewById(R.id.evaluation_take_photo2);
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
                if (size > 0) {
                    getTakePhoto().onEnableCompress(compressConfig, true);
                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder().setWithOwnGallery(true);
                    getTakePhoto().setTakePhotoOptions(builder.create());
                    getTakePhoto().onPickMultiple(6 - imageViews.size());
                } else {
                    Toast.makeText(this, "最多只能选择6张", Toast.LENGTH_SHORT).show();
                }
//                getTakePhoto().onPickFromDocuments();
                //customHelper.onClick(v,getTakePhoto());
                break;
            case R.id.evaluation_take_photo2:
//                View contentView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
//                CustomHelper customHelper = CustomHelper.of(contentView);
                //CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setApectY(1).setWithOwnCrop(true).create();
                int size2 = 6 - imageViews.size();
                if (size2 > 0) {
                    getTakePhoto().onEnableCompress(compressConfig, true);
                    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder().setWithOwnGallery(true);
                    getTakePhoto().setTakePhotoOptions(builder.create());
                    getTakePhoto().onPickMultiple(6 - imageViews.size());
                } else {
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
        ImageView imageView = new ImageView(this);
        picasso.load(new File(result.getImages().get(0).getOriginalPath())).into(imageView);
       // store_images.addView(imageView);
        InitStoreImages(result.getImages());

    }

    private void InitStoreImages(List<TImage> list) {
        int width = (ScreenUtils.getScreenWidth(this)- CommonUtils.dip2px(this,12))/ 4;
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageViewsurl.add(list.get(i).getOriginalPath());
            imageView.setTag(R.id.tag_first, list.get(i).getOriginalPath());
            imageView.setTag(R.id.tag_second, list.get(i).getCompressPath());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            picasso.load(new File(list.get(i).getCompressPath())).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EvaluationActivity.this, EvaluationDetailImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imgeurl", v.getTag(R.id.tag_first).toString());
                    intent.putExtras(bundle);
                    String transitionName = "images";
                    ActivityOptionsCompat activityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(EvaluationActivity.this, v, transitionName);
                    startActivityForResult(intent, 1, activityOptionsCompat.toBundle());
                }
            });
            imageViews.add(imageView);
            if (store_images.getChildCount() < 4) {
                store_images.addView(imageView);
                if(store_images.getChildCount() ==4)
                {
                    evaluation_take_photo.setVisibility(View.GONE);
                    evaluation_take_photo2.setVisibility(View.VISIBLE);
                }
            } else {
                if (evaluation_take_photo.getVisibility() == View.VISIBLE) {
                    evaluation_take_photo.setVisibility(View.GONE);
                    evaluation_take_photo2.setVisibility(View.VISIBLE);
                } else {
                }
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
                                if (i < 4) {
                                    store_images.removeView(imageViews.get(i));
                                    evaluation_take_photo.setVisibility(View.VISIBLE);
                                    evaluation_take_photo2.setVisibility(View.GONE);
                                } else {
                                    store_images2.removeView(imageViews.get(i));
                                    evaluation_take_photo.setVisibility(View.GONE);
                                    evaluation_take_photo2.setVisibility(View.VISIBLE);
                                }
                                imageViewsurl.remove(i);
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
