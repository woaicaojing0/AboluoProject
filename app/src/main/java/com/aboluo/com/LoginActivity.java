package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by CJ on 2016/9/21.
 */

public class LoginActivity extends Activity  implements View.OnClickListener{
   private TextView txt_register;
    private TextView txt_retrivepwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        init();
        txt_register.setOnClickListener(this);
        txt_retrivepwd.setOnClickListener(this);
    }
    private  void init()
    {
        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_retrivepwd = (TextView) findViewById(R.id.txt_retrivepwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_retrivepwd:
                Intent intent2 = new Intent(this,RetrievepwdActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
