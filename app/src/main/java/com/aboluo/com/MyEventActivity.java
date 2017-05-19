package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by cj34920 on 2017/5/18.
 */

public class MyEventActivity extends Activity implements View.OnClickListener {
    private ImageView unary_image, groupbuy_image, event_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event);
        unary_image = (ImageView) findViewById(R.id.unary_image);
        groupbuy_image = (ImageView) findViewById(R.id.groupbuy_image);
        event_back = (ImageView) findViewById(R.id.event_back);
        unary_image.setOnClickListener(this);
        groupbuy_image.setOnClickListener(this);
        event_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.unary_image:
                Intent intent = new Intent(this, MyUnaryRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.groupbuy_image:
                Intent intent2 = new Intent(this, MyGroupRecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.event_back:
                finish();
                break;
            default:
                break;
        }
    }
}
