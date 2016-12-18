package com.aboluo.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
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
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_acticvity);
        webView = (WebView) findViewById(R.id.test_webview);
        webView.loadUrl("http://back.aboluomall.com/Moblie/ShowEvaluation?goodsId=12");
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
