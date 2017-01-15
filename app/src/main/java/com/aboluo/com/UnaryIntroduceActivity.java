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
 */

public class UnaryIntroduceActivity extends Activity {
    private LargeImageView lgiv_unaryintroduce;
    private ImageView iv_unaryintroduce_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unaryintroduce);
        lgiv_unaryintroduce = (LargeImageView) findViewById(R.id.lgiv_unaryintroduce);
        iv_unaryintroduce_back = (ImageView) findViewById(R.id.iv_unaryintroduce_back);
        //通过流的方式加载assets文件夹里面的大图
        try {
            lgiv_unaryintroduce.setImage(new InputStreamBitmapDecoderFactory(getAssets().open("unaryintroduce.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lgiv_unaryintroduce.setEnabled(true);
        iv_unaryintroduce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
