package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by CJ on 2016/9/21.
 */

public class RetrievepwdActivity  extends Activity{
    private TextView retrievepwd_btn_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.retrievepwd);
        retrievepwd_btn_close = (TextView) findViewById(R.id.retrievepwd_btn_close);
        retrievepwd_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrievepwdActivity.this.finish();
            }
        });
    }
}
