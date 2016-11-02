package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aboluo.Gesture.SecondActivity;
import com.aboluo.GestureUtils.Contants;
import com.aboluo.GestureUtils.PasswordUtil;
import com.leo.gesturelibray.enums.LockMode;
import com.leo.gesturelibray.util.StringUtils;

/**
 * Created by CJ on 2016/9/24.
 */

public class MyInfoAcitvity extends Activity implements View.OnClickListener {
    private TextView my_info_text_back;
    private Switch gesture;
    private SharedPreferences sharedPreferences;
private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        init();
        my_info_text_back.setOnClickListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyInfoAcitvity.this);
        pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
        if (pwd.equals("0")) {}
        else {
            gesture.setChecked(true);
        }
        gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pwd = sharedPreferences.getString(Contants.PASS_KEY, "0");
                if (isChecked) {
                    if (pwd.equals("0")) {
                        actionSecondActivity(LockMode.SETTING_PASSWORD);
                    }else {}
                } else {


                    if (pwd.equals("0")) {
                    } else {
                        actionSecondActivity(LockMode.VERIFY_PASSWORD);
                    }
                }
            }
        });
    }

    private void init() {
        my_info_text_back = (TextView) findViewById(R.id.my_info_text_back);
        gesture = (Switch) findViewById(R.id.gesture);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_text_back:
                this.finish();
            default:
                break;
        }
    }

    /**
     * 跳转到密码处理界面
     */
    private void actionSecondActivity(LockMode mode) {
        if (mode != LockMode.SETTING_PASSWORD) {
            if (StringUtils.isEmpty(PasswordUtil.getPin(this))) {
                Toast.makeText(getBaseContext(), "请先设置密码", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (mode == LockMode.VERIFY_PASSWORD) {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivityForResult(intent, 1);

        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(Contants.INTENT_SECONDACTIVITY_KEY, mode);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int result = data.getIntExtra("startgesture", 3);
        if(result == 0) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.remove(Contants.PASS_KEY);
//            editor.commit();
            gesture.setChecked(false);
        }
    }
}
