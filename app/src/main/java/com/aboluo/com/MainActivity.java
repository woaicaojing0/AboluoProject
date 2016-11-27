package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.fragment.IndexFragment;
import com.aboluo.fragment.MenuFragment;
import com.aboluo.fragment.MyFragment;
import com.aboluo.fragment.ShopCarFragment;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FragmentTransaction fragmentTransaction;
    private IndexFragment indexFragment;
    private MenuFragment menufragment;
    private MyFragment myfragment;
    private ShopCarFragment shopcarfragment;
    private Fragment mNowFragment;
    private LinearLayout index_linelayout, menu_linelayout, shopcar_linelayout, my_linelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        index_linelayout.setOnClickListener(this);
        menu_linelayout.setOnClickListener(this);
        shopcar_linelayout.setOnClickListener(this);
        my_linelayout.setOnClickListener(this);
    }

    private void SwitchFragment(int id) {
        switch (id) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                switchContent(shopcarfragment);
                break;
            case 4:
                break;
        }
    }

    private void init() {
        MyApplication.init(this);
        index_linelayout = (LinearLayout) findViewById(R.id.index_bottom_index);
        menu_linelayout = (LinearLayout) findViewById(R.id.index_bottom_menu);
        shopcar_linelayout = (LinearLayout) findViewById(R.id.index_bottom_shopcar);
        my_linelayout = (LinearLayout) findViewById(R.id.index_bottom_me);

        indexFragment = new IndexFragment();
        menufragment = new MenuFragment();
        myfragment = new MyFragment();
        shopcarfragment = new ShopCarFragment();
        indexFragment = new IndexFragment();
        mNowFragment = indexFragment;
        FristChangeFragment(indexFragment);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.index_bottom_index:
                switchContent(indexFragment);
                break;
            case R.id.index_bottom_menu:
                switchContent(menufragment);
                break;
            case R.id.index_bottom_shopcar:
                if (CommonUtils.IsLogin(MainActivity.this)) {
                    switchContent(shopcarfragment);
                } else {
                    Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.index_bottom_me:
                if (CommonUtils.IsLogin(MainActivity.this)) {
                    switchContent(myfragment);
                } else {
                    Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }
    }

    private void switchContent(Fragment fragment) {
        if (mNowFragment != fragment) {
            fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!fragment.isAdded()) {
                fragmentTransaction.remove(mNowFragment).add(R.id.content, fragment).commit();

            } else {
                fragmentTransaction.remove(mNowFragment).add(R.id.content, fragment).commit();
            }
        } else {
        }
        mNowFragment = fragment;
    }

    private void FristChangeFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, fragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int id = intent.getIntExtra("id", 1);
        SwitchFragment(id);
    }
}
