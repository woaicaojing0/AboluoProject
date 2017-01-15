package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CleanUtils;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2017/1/15.
 */

public class SettingActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rl_setting_aboutus, rl_setting_cleancache;
    private ImageView iv_setting_back;
    private TextView tv_cache_size;
    private SweetAlertDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settting);
        init();
        rl_setting_aboutus.setOnClickListener(this);
        rl_setting_cleancache.setOnClickListener(this);
        iv_setting_back.setOnClickListener(this);
        try {
            tv_cache_size.setText( CleanUtils.getTotalCacheSize(SettingActivity.this).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting_aboutus:
                Intent intent = new Intent(SettingActivity.this, CompanyIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_setting_cleancache:
                pdialog.show();
                if(CleanUtils.cleanInternalCache())
                {
                    Toast.makeText(this, "清理缓存成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "清理缓存失败", Toast.LENGTH_SHORT).show();
                }
                try {
                    tv_cache_size.setText( CleanUtils.getTotalCacheSize(SettingActivity.this).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pdialog.dismiss();
                break;
            case R.id.iv_setting_back:
                finish();
                break;
        }

    }

    private void init() {
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("清理中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        rl_setting_aboutus = (RelativeLayout) findViewById(R.id.rl_setting_aboutus);
        rl_setting_cleancache = (RelativeLayout) findViewById(R.id.rl_setting_cleancache);
        iv_setting_back = (ImageView) findViewById(R.id.iv_setting_back);
        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
    }
}
