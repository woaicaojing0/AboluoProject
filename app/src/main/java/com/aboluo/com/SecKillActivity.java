package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.adapter.SecKillDetailAdapter;
import com.aboluo.fragment.SecKillFragment.CenterFragment;
import com.aboluo.fragment.SecKillFragment.LeftFragment;
import com.aboluo.fragment.SecKillFragment.RightFragment;
import com.aboluo.model.SecKillAllInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj34920 on 2016/11/24.
 */

public class SecKillActivity extends FragmentActivity {
    private TabLayout tabLayout_seckilldetail;
    private ViewPager viewpage_seckilldetail;
    private Fragment leftfragment, rightfragment, centerfragment;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;
    private SecKillDetailAdapter fAdapter;
    private ArrayList<SecKillAllInfo.SkillMainListBean> listseckillbean;
    private int selected;
    private LinearLayout back_miaosha_choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seckill);
        Intent intent = getIntent();
        selected = intent.getIntExtra("TAG", 0);
        selected = intent.getIntExtra("changci", 0);
        Bundle bundle = intent.getExtras();
        listseckillbean = bundle.getParcelableArrayList("data");
        init();
        back_miaosha_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        tabLayout_seckilldetail = (TabLayout) findViewById(R.id.tabLayout_seckilldetail);
        viewpage_seckilldetail = (ViewPager) findViewById(R.id.vp_seckilldetail);
        back_miaosha_choose = (LinearLayout) findViewById(R.id.back_miaosha_choose);
        inintValue();
    }

    private void inintValue() {
        list_title = new ArrayList<>();
        if(listseckillbean ==null)
        {
            Toast.makeText(this, "listseckillbean is null ", Toast.LENGTH_SHORT).show();}else {
            for (int i = 0; i < listseckillbean.size(); i++) {
                if (listseckillbean.get(i).getStartTime().length() > 18) {
                    list_title.add(listseckillbean.get(i).getStartTime().substring(11, 16));
                }
            }
            //初始化fragment
            leftfragment = new LeftFragment();
            rightfragment = new RightFragment();
            centerfragment = new CenterFragment();
            if (listseckillbean.size() >= 3) {
                Bundle bundle0 = new Bundle();
                bundle0.putParcelable("data", listseckillbean.get(2));
                rightfragment.setArguments(bundle0);

                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("data", listseckillbean.get(0));
                leftfragment.setArguments(bundle1);

                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("data", listseckillbean.get(1));
                centerfragment.setArguments(bundle2);
                //将fragment装进列表中
                list_fragment = new ArrayList<>();
                list_fragment.add(leftfragment);
                list_fragment.add(centerfragment);
                list_fragment.add(rightfragment);
                //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
//        list_title.add("11:00");
//        list_title.add("12:00");
//        list_title.add("13:00");
                //设置TabLayout的模式
                tabLayout_seckilldetail.setTabMode(TabLayout.MODE_FIXED);
                //为TabLayout添加tab名称
                tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(0)), false);
                tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(1)), false);
                tabLayout_seckilldetail.addTab(tabLayout_seckilldetail.newTab().setText(list_title.get(2)), false);
                fAdapter = new SecKillDetailAdapter(getSupportFragmentManager(), list_fragment, list_title);
                //viewpager加载adapter
                viewpage_seckilldetail.setAdapter(fAdapter);
                //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
                //TabLayout加载viewpager
                tabLayout_seckilldetail.setupWithViewPager(viewpage_seckilldetail);
                //tab_FindFragment_title.set
                viewpage_seckilldetail.setCurrentItem(selected);
                setupTabIcons();
            } else {
                Toast.makeText(this, "当前场次没有三场", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupTabIcons() {
        tabLayout_seckilldetail.getTabAt(0).setCustomView(getTabView(0));
        tabLayout_seckilldetail.getTabAt(1).setCustomView(getTabView(1));
        tabLayout_seckilldetail.getTabAt(2).setCustomView(getTabView(2));
    }

    //0,1,2 //(0-未开启，1-已开启，2-准备中，3-已结束
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tablayout_item, null);
        TextView txt_title = (TextView) view.findViewById(R.id.tablayout_title);
        txt_title.setText(list_title.get(position));
        TextView txt_status = (TextView) view.findViewById(R.id.tablayout_status);
        String stauts = listseckillbean.get(position).getState();
        if (stauts.equals("0") || stauts.equals("2")) {
            txt_status.setText("未开启");
        } else if (stauts.equals("1")) {
            txt_status.setText("已开启");
        } else {
            txt_status.setText("已结束");
        }
        return view;
    }
}
