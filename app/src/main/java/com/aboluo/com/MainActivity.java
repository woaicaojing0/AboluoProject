package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.aboluo.fragment.IndexFragment;
import com.aboluo.fragment.MenuFragment;
import com.aboluo.fragment.MyFragment;
import com.aboluo.fragment.ShopCarFragment;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import me.drakeet.materialdialog.MaterialDialog;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FragmentTransaction fragmentTransaction;
    private IndexFragment indexFragment;
    private MenuFragment menufragment;
    private MyFragment myfragment;
    private ShopCarFragment shopcarfragment;
    private Fragment mNowFragment;
    private LinearLayout index_linelayout, menu_linelayout, shopcar_linelayout, my_linelayout;
    private long exitTime = 0;
    private ImageView iv_menu_home, iv_bottom_menu, iv_menu_car, iv_menu_me;
    private TextView tv_menu_home, tv_bottom_menu, tv_menu_car, tv_menu_me;

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
        checkUpdate();
    }

    private void SwitchFragment(int id) {
        CleanBootomButton();
        switch (id) {
            case 1:
                iv_menu_home.setBackground(getResources().getDrawable(R.drawable.sort_light_fill));
                tv_menu_home.setTextColor(getResources().getColor(R.color.btn_color));
                switchContent(indexFragment);
                break;
            case 2:
                iv_bottom_menu.setBackground(getResources().getDrawable(R.drawable.sort_light_fill));
                tv_bottom_menu.setTextColor(getResources().getColor(R.color.btn_color));
                switchContent(menufragment);
                break;
            case 3:
                iv_menu_car.setBackground(getResources().getDrawable(R.drawable.cart_fill));
                tv_menu_car.setTextColor(getResources().getColor(R.color.btn_color));
                switchContent(shopcarfragment);
                break;
            case 4:
                break;
        }
    }

    private void init() {
        initBootomButton();
        MyApplication.init(this);
        index_linelayout = (LinearLayout) findViewById(R.id.index_bottom_index);
        menu_linelayout = (LinearLayout) findViewById(R.id.index_menu_menu);
        shopcar_linelayout = (LinearLayout) findViewById(R.id.index_bottom_shopcar);
        my_linelayout = (LinearLayout) findViewById(R.id.index_bottom_me);
        indexFragment = new IndexFragment();
        menufragment = new MenuFragment();
        myfragment = new MyFragment();
        shopcarfragment = new ShopCarFragment();
        indexFragment = new IndexFragment();
        mNowFragment = indexFragment;
        iv_menu_home.setBackground(getResources().getDrawable(R.drawable.home_fill));
        tv_menu_home.setTextColor(getResources().getColor(R.color.btn_color));
        FristChangeFragment(indexFragment);
    }

    private void initBootomButton() {
        iv_menu_home = (ImageView) findViewById(R.id.iv_menu_home);
        iv_bottom_menu = (ImageView) findViewById(R.id.iv_bottom_menu);
        iv_menu_car = (ImageView) findViewById(R.id.iv_menu_car);
        iv_menu_me = (ImageView) findViewById(R.id.iv_menu_me);
        tv_menu_home = (TextView) findViewById(R.id.tv_menu_home);
        tv_bottom_menu = (TextView) findViewById(R.id.tv_bottom_menu);
        tv_menu_car = (TextView) findViewById(R.id.tv_menu_car);
        tv_menu_me = (TextView) findViewById(R.id.tv_menu_me);

    }

    @Override
    public void onClick(View view) {
        CleanBootomButton();
        switch (view.getId()) {
            case R.id.index_bottom_index:
                iv_menu_home.setBackground(getResources().getDrawable(R.drawable.home_fill));
                tv_menu_home.setTextColor(getResources().getColor(R.color.btn_color));
                switchContent(indexFragment);
                break;
            case R.id.index_menu_menu:
                iv_bottom_menu.setBackground(getResources().getDrawable(R.drawable.sort_light_fill));
                tv_bottom_menu.setTextColor(getResources().getColor(R.color.btn_color));
                switchContent(menufragment);
                break;
            case R.id.index_bottom_shopcar:
                if (CommonUtils.IsLogin(MainActivity.this)) {
                    iv_menu_car.setBackground(getResources().getDrawable(R.drawable.cart_fill));
                    tv_menu_car.setTextColor(getResources().getColor(R.color.btn_color));
                    switchContent(shopcarfragment);
                } else {
                    Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.index_bottom_me:
                if (CommonUtils.IsLogin(MainActivity.this)) {
                    iv_menu_me.setBackground(getResources().getDrawable(R.drawable.my_fill));
                    tv_menu_me.setTextColor(getResources().getColor(R.color.btn_color));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                int pid = android.os.Process.myPid();    //获取当前应用程序的PID
                android.os.Process.killProcess(pid);    //杀死当前进程
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void CleanBootomButton() {
        iv_menu_home.setBackground(getResources().getDrawable(R.drawable.home));
        iv_bottom_menu.setBackground(getResources().getDrawable(R.drawable.sort_light));
        iv_menu_car.setBackground(getResources().getDrawable(R.drawable.cart));
        iv_menu_me.setBackground(getResources().getDrawable(R.drawable.my));

        tv_menu_home.setTextColor(getResources().getColor(R.color.txtColor));
        tv_bottom_menu.setTextColor(getResources().getColor(R.color.txtColor));
        tv_menu_car.setTextColor(getResources().getColor(R.color.txtColor));
        tv_menu_me.setTextColor(getResources().getColor(R.color.txtColor));
    }
    private void checkUpdate(){
        PgyUpdateManager.register(MainActivity.this,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        final MaterialDialog updateDialog = new MaterialDialog(MainActivity.this);
                        updateDialog.setTitle("更新提示");
                        updateDialog.setMessage("发现新版本！");
                        updateDialog.setPositiveButton("马上升级", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {   //进行更新
                                startDownloadTask(
                                        MainActivity.this,
                                        appBean.getDownloadURL());
                            }
                        });

                        updateDialog.setNegativeButton("以后再说", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {   //取消更新
                                updateDialog.dismiss();
                            }
                        });

                        updateDialog.show();

                    }

                    @Override
                    public void onNoUpdateAvailable() {

                    }
                });
    }

}
