package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.XUtils.CommonUtils;
import com.aboluo.XUtils.MyApplication;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CJ on 2016/12/13.
 */

public class UpdateMyInfoActivity extends Activity {
    private TextView update_myinfo_txt;
    private EditText my_info_edit;
    private Button my_info_update_save;
    private RequestQueue requestQueue;
    private String ImageUrl;
    private String URL;
    private String APPToken;
    private Gson gson;
    private Picasso picasso;
    private SweetAlertDialog pdialog;
    private String MemberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemyinfo);
        init();
        my_info_update_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname  = my_info_edit.getText().toString();
                if(nickname.length() >0)
                {
                    Intent intent =getIntent();
                    intent.putExtra("nickname",nickname);
                    setResult(RESULT_OK,intent);
                    finish();
                }else
                {
                    Toast.makeText(UpdateMyInfoActivity.this, "请填写相关信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {

        my_info_update_save = (Button) findViewById(R.id.my_info_update_save);
        my_info_edit = (EditText) findViewById(R.id.my_info_edit);
        update_myinfo_txt = (TextView) findViewById(R.id.update_myinfo_txt);
        MemberId = CommonUtils.GetMemberId(this);
        requestQueue = MyApplication.getRequestQueue();
        ImageUrl = CommonUtils.GetValueByKey(this, "ImgUrl");
        URL = CommonUtils.GetValueByKey(this, "apiurl");
        APPToken = CommonUtils.GetValueByKey(this, "APPToken");
        picasso = Picasso.with(this);
        gson = new Gson();
        pdialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pdialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pdialog.setTitleText("加载中");
        pdialog.setCanceledOnTouchOutside(true);
        pdialog.setCancelable(true);
        Intent intent = getIntent();
        my_info_edit.setText(intent.getStringExtra("old"));
    }

}
