package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aboluo.XUtils.ValidateUtils;

/**
 * Created by CJ on 2016/10/4.
 */

public class AddAddressActivity extends Activity implements TextWatcher {
    private Button add_address_save;
    private EditText edit_receive_name, edit_receive_phone, edit_receive_address, edit_receive_zipcode;

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
        edit_receive_name.addTextChangedListener(this);
        edit_receive_phone.addTextChangedListener(this);
        edit_receive_address.addTextChangedListener(this);
        edit_receive_zipcode.addTextChangedListener(this);
    }

    private void init() {
        add_address_save = (Button) findViewById(R.id.add_address_save);
        edit_receive_name = (EditText) findViewById(R.id.edit_receive_name);
        edit_receive_phone = (EditText) findViewById(R.id.edit_receive_phone);
        edit_receive_address = (EditText) findViewById(R.id.edit_receive_address);
        edit_receive_zipcode = (EditText) findViewById(R.id.edit_receive_zipcode);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean isok = false;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        boolean isok = false;
        if (ValidateUtils.isMobileNO(edit_receive_phone.getText().toString())) {
            isok = true;
            if (ValidateUtils.Zipcode(edit_receive_zipcode.getText().toString())) {
                isok = true;
                if (edit_receive_name.length() > 2) {
                    isok = true;
                    if (edit_receive_address.length() > 0) {
                        isok = true;
                    } else {
                        isok = false;
                    }
                } else {
                    isok = false;
                }
            } else {
                isok = false;
            }
        } else {
            isok = false;
        }
        if (isok) {
            add_address_save.setEnabled(true);
        } else {
            add_address_save.setEnabled(false);
        }
    }
}
