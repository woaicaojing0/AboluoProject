package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboluo.XUtils.ScreenUtils;

import org.w3c.dom.Text;

/**
 * Created by cj34920 on 2016/12/1.
 */

public class SignInInfoActivity extends Activity {
    private TextView txt_siginfodetail, txt_SureOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_signininfo, null);
        setContentView(view);
        int screenWidth = ScreenUtils.getScreenWidth(this);
        //两种设置dialog 的宽度方法
        view.setLayoutParams(new FrameLayout.LayoutParams((screenWidth / 5) * 4, ViewGroup.LayoutParams.WRAP_CONTENT));
//        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        p.width = (int) (screenWidth * 0.7); // 宽度设置为屏幕的0.7
//        getWindow().setAttributes(p);
        init();
        String result = "1.微信签到会更有惊喜。" +
                "\n2.每小时都会放更多积分，如果运气不好没领到，下次请早点来。" +
                "\n3.超过半年未下单的会员们，将不能领取更多的积分。" +
                "\n4.对不正当手段（包括但不限于作弊。扰乱系统、网络攻击等）参与活动的用户，阿波罗有权禁止其参与活动。取消其获得资格（如奖励已发送，阿波罗有权追回）";
        txt_siginfodetail.setText(result);
        txt_SureOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void init() {
        txt_siginfodetail = (TextView) findViewById(R.id.txt_siginfodetail);
        txt_SureOut = (TextView) findViewById(R.id.txt_SureOut);
    }
}
