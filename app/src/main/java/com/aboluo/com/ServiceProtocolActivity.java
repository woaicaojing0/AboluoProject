package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;

/**
 * Created by CJ on 2017/1/14.
 * 服务协议介绍
 */

public class ServiceProtocolActivity extends Activity {
    private LargeImageView lgiv_serviceprotocol;
    private ImageView iv_serviceprotocol_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprotocol);
        lgiv_serviceprotocol = (LargeImageView) findViewById(R.id.lgiv_serviceprotocol);
        iv_serviceprotocol_back = (ImageView) findViewById(R.id.iv_serviceprotocol_back);
        //通过流的方式加载assets文件夹里面的大图
        try {
            lgiv_serviceprotocol.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("serviceprotrcol.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lgiv_serviceprotocol.setEnabled(true);
        iv_serviceprotocol_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
