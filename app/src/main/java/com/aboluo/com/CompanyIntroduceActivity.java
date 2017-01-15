package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;

/**
 * Created by CJ on 2017/1/15.
 */

public class CompanyIntroduceActivity extends Activity {
    private LargeImageView lgiv_companyintroduce;
    private ImageView iv_companyintroduce_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyintroduce);
        lgiv_companyintroduce = (LargeImageView) findViewById(R.id.lgiv_companyintroduce);
        iv_companyintroduce_back = (ImageView) findViewById(R.id.iv_companyintroduce_back);
        //通过流的方式加载assets文件夹里面的大图
        try {
            lgiv_companyintroduce.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("companyintroduce.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lgiv_companyintroduce.setEnabled(true);
        iv_companyintroduce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
