package com.aboluo.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.adapter.BigImageViewPageAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by CJ on 2016/10/25.
 */

public class GoodsDetailImageActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private PhotoView goods_type_bigimage;
    private LinearLayout all_bigimge;
    private PhotoViewAttacher photoViewAttacher;
    ViewPager mViewPager;
    List<ImageView> imageViews;
    ArrayList<String> listurl;
    int  position;
    private ViewPager vp;
    private TextView hint;
    private TextView save;
    private BigImageViewPageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_image);
        vp = (ViewPager) this.findViewById(R.id.viewPager);
        hint = (TextView) this.findViewById(R.id.hint);
//        save = (TextView) this.findViewById(R.id.save);
//        save.setOnClickListener(this);
        //Intent intent = getIntent();
//        String url = intent.getStringExtra("imgeurl");
//        listurl = intent.getStringArrayListExtra("imgeurl");
//        goods_type_bigimage = (PhotoView) findViewById(R.id.goods_type_bigimage);
//        Picasso.with(this).load(url).placeholder(getResources().getDrawable(R.drawable.imagviewloading))
//                .error(getResources().getDrawable(R.drawable.imageview_error))
//                .into(goods_type_bigimage);
//        all_bigimge = (LinearLayout) findViewById(R.id.all_bigimge);
//        photoViewAttacher = new PhotoViewAttacher(goods_type_bigimage);
//        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//            @Override
//            public void onPhotoTap(View arg0, float arg1, float arg2) {
//                finish();
//            }
//        });
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        mViewPager.setAdapter(new ImageAdapter(this));
        //获取data
        Intent intent = getIntent();
        listurl = intent.getStringArrayListExtra("imgeurl");
        position = intent.getIntExtra("position",0);
//设置ViewPager
        adapter = new BigImageViewPageAdapter(this,listurl);
        vp.setAdapter(adapter);
        vp.setCurrentItem(position);
        vp.addOnPageChangeListener(this);
        hint.setText(position + 1 + "/" + listurl.size());
        if(  Build.VERSION.SDK_INT <=20 ) {
            Toast.makeText(this, "当前手机版本过低，可能无法缩放", Toast.LENGTH_SHORT).show();
        }else {}
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        hint.setText(position + 1 + "/" +listurl.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }
}
