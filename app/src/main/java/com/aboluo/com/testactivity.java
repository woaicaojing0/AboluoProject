package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

/**
 * Created by CJ on 2016/12/1.
 */

public class testactivity extends TakePhotoActivity implements View.OnClickListener {
    private View view;
    private RelativeLayout my_info_image;
    private CustomHelper customHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        my_info_image = (RelativeLayout) findViewById(R.id.my_info_image);
        view= LayoutInflater.from(this).inflate(R.layout.common_layout,null);
        customHelper=CustomHelper.of(view);
        my_info_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        customHelper.onClick(v,getTakePhoto());
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
        Intent intent=new Intent(this,ResultActivity.class);
        intent.putExtra("images",images);
        startActivity(intent);
    }
}
