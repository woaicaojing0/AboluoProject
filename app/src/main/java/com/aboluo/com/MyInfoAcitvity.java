package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by CJ on 2016/9/24.
 */

public class MyInfoAcitvity extends Activity implements View.OnClickListener{
    private TextView my_info_text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        init();
        my_info_text_back.setOnClickListener(this);
    }
    private void init()
    {
        my_info_text_back = (TextView) findViewById(R.id.my_info_text_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.my_info_text_back:
                this.finish();
                default:
                    break;
        }
    }
}
