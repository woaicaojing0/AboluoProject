package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by CJ on 2016/10/25.
 */

public class GoodsDetailImageActivity extends Activity {
    private PhotoView goods_type_bigimage;
    private LinearLayout all_bigimge;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_image);
        Intent intent = getIntent();
       String url =  intent.getStringExtra("imgeurl");
        goods_type_bigimage = (PhotoView) findViewById(R.id.goods_type_bigimage);
        Picasso.with(this).load(url).placeholder(getResources().getDrawable(R.drawable.imagviewloading))
                .error(getResources().getDrawable(R.drawable.imageview_error))
                .into(goods_type_bigimage);
        all_bigimge = (LinearLayout) findViewById(R.id.all_bigimge);
        photoViewAttacher = new PhotoViewAttacher(goods_type_bigimage);
        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                finish();
            }
        });


    }
}
