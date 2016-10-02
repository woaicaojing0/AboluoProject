package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by CJ on 2016/10/2.
 */

public class OneYuanAcitvity  extends Activity implements View.OnClickListener{
    private ImageView oneyuan_text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneyuan);
        init();
        oneyuan_text_back.setOnClickListener(this);
    }
    private  void init()
    {
        oneyuan_text_back = (ImageView) findViewById(R.id.oneyuan_text_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.oneyuan_text_back:
                finish();break;
            default:
                break;
        }
    }
}
