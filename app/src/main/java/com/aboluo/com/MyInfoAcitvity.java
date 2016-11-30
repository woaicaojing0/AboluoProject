package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.Gesture.SecondActivity;
import com.aboluo.GestureUtils.Contants;
import com.aboluo.GestureUtils.PasswordUtil;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by CJ on 2016/9/24.
 */

public class MyInfoAcitvity extends TakePhotoActivity implements View.OnClickListener {
    private TextView my_info_text_back;
    private Switch gesture;
    private SharedPreferences sharedPreferences;
    private String pwd;
    private SharedPreferences.Editor editor;
    private RelativeLayout my_info_image;
    private CustomHelper customHelper;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        view = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        init();
        customHelper = CustomHelper.of(view);
        my_info_text_back.setOnClickListener(this);
        my_info_image.setOnClickListener(this);


        pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
        if (pwd.equals("0")) {
        } else {
            gesture.setChecked(true);
        }
        gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
                if (isChecked) {
                    editor.putBoolean("isstartgesture", true);
                    editor.commit();
                    if (pwd.equals("0")) {
                        actionSecondActivity(LockMode.SETTING_PASSWORD);
                    } else {
                    }
                } else {
                    editor.putBoolean("isstartgesture", false);
                    editor.commit();
                    if (pwd.equals("0")) {
                    } else {
                        actionSecondActivity(LockMode.VERIFY_PASSWORD);
                    }
                }
            }
        });
    }

    private void init() {
        my_info_text_back = (TextView) findViewById(R.id.my_info_text_back);
        my_info_image = (RelativeLayout) findViewById(R.id.my_info_image);
        gesture = (Switch) findViewById(R.id.gesture);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyInfoAcitvity.this);
        editor = sharedPreferences.edit();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_text_back:
                this.finish();
                break;
            case R.id.my_info_image:
//                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
//                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
//                Uri imageUri = Uri.fromFile(file);
//                CropOptions cropOptions=new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
//                getTakePhoto().onPickFromDocumentsWithCrop(imageUri,cropOptions);
                customHelper.onClick(view, getTakePhoto());
//                getTakePhoto().onPickFromGallery();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (mode == LockMode.VERIFY_PASSWORD) {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("myinfo", true);
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivityForResult(intent, 1);

        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (data == null) {
        } else {
            int result = data.getIntExtra("startgesture", 3);
            if (result == 0) {
                gesture.setChecked(false);
            }
        }
    }



    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("images", images);
        startActivity(intent);
    }
}
