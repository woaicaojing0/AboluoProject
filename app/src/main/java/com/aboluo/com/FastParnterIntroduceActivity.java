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
 * 快速成为合伙人 服务协议介绍界面
 */

public class FastParnterIntroduceActivity extends Activity {
    private LargeImageView lgiv_fast_partner_introduce;
    private ImageView iv_fast_parnter_introduce_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastparnterintroduce);
        lgiv_fast_partner_introduce = (LargeImageView) findViewById(R.id.lgiv_fast_partner_introduce);
        iv_fast_parnter_introduce_back = (ImageView) findViewById(R.id.iv_fast_parnter_introduce_back);
        //通过流的方式加载assets文件夹里面的大图
        try {
            lgiv_fast_partner_introduce.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("fastparnterintroduce.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lgiv_fast_partner_introduce.setEnabled(true);
        iv_fast_parnter_introduce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
