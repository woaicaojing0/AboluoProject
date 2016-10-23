package com.aboluo.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.aboluo.com.R;

/**
 * @author kince
 * @category 模仿蘑菇街购物车动画效果 使用Tween动画
 * @issue 1、第一次执行动画效果图片放大效果明显，之后放大效果不明显，蘑菇街也有这样的问题。
 *        2、弹出的popubWindow变形 希望对这方面了解的朋友告知一下
 *
 */
public class AnimationActivtiy extends Activity {

    private ImageView mAnimImageView;
    private TextView mTextView;
    private TextView mNumTextView;
    private Animation mAnimation;
    private PopupWindow mPopupWindow;
    private int goodsNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_frame_layout);

        mAnimImageView = (ImageView) findViewById(R.id.cart_anim_icon);
        mTextView = (TextView) findViewById(R.id.detail_cart_btn);
        mNumTextView = (TextView) findViewById(R.id.detail_shopping_new);
        mTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mAnimImageView.setVisibility(View.VISIBLE);
                mAnimImageView.startAnimation(mAnimation);
            }
        });
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.cart_anim);
        mAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                goodsNum++;
                mNumTextView.setText(goodsNum+"");
                mAnimImageView.setVisibility(View.INVISIBLE);
                createPopbWindow();
                mPopupWindow.showAtLocation(mAnimImageView, Gravity.CENTER
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mAnimImageView.setVisibility(View.VISIBLE);
                mAnimImageView.startAnimation(mAnimation);

            }
        }, 1500);
    }

    private void createPopbWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.cart_popup_layout, null);
        contentview.setFocusable(true);
        contentview.setFocusableInTouchMode(true);
        mPopupWindow = new PopupWindow(this);
        mPopupWindow.setContentView(contentview);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
    }

}
