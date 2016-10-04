package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddAddressActivity extends Activity {
    private Button add_address_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_add_address);
        init();
        add_address_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddAddressActivity.this, "你点击我", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init()
    {
        add_address_save = (Button) findViewById(R.id.add_address_save);
    }
}
