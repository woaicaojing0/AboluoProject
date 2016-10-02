package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by CJ on 2016/10/2.
 */

public class MiaoShaActivity extends Activity implements View.OnClickListener{
    private ImageView all_miaoshao_text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_miaoshao);
        init();
        all_miaoshao_text_back.setOnClickListener(this);
    }
    private void  init(){
        all_miaoshao_text_back = (ImageView) findViewById(R.id.all_miaoshao_text_back);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.all_miaoshao_text_back:
                finish();
                break;
            default:
                break;
        }
    }
}
