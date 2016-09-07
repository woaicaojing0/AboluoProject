package com.aboluo.com;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.adapter.BannerAdapter;
import com.aboluo.adapter.GridViewAdapter;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private LinearLayout linearLayout;
    private EditText top_editsearch;
    private RollPagerView rollPagerView;
    private GridView mid_gridview;
    private MarqueeView marqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linearLayout.setFocusable(true);
                linearLayout.setFocusableInTouchMode(true);
                linearLayout.requestFocus();
                return false;
            }
        });
        //edittext 中的图标
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 80, 80);
        top_editsearch.setCompoundDrawables(drawable, null, drawable, null);
        //头部滚动banner
        rollPagerView.setHintView(new ColorPointHintView(this, Color.RED, Color.WHITE));
        rollPagerView.setAdapter(new BannerAdapter());
        rollPagerView.setFocusable(false);
        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
            }
        });
        mid_gridview.setAdapter(new GridViewAdapter(MainActivity.this));
        final List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是曹晶 \n 2.欢迎大家关注我哦！");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：woaicaojing0");
        info.add("4. 新浪微博：曹晶微博");
        info.add("5. 个人博客：caojing.com");
        info.add("6. 微信公众号：woaiocajing0");
        marqueeView.startWithList(info);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(MainActivity.this, info.get(position).toString()+textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void init()
    {
        linearLayout = (LinearLayout) findViewById(R.id.ALLFather);
        top_editsearch = (EditText) findViewById(R.id.top_editsearch);
        rollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);
        mid_gridview = (GridView) findViewById(R.id.mid_gridview);
        marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
    }
}
