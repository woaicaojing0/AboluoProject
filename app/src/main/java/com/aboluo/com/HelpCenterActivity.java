package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aboluo.com.WebActivity.FeedBackActivity;

/**
 * Created by CJ on 2017/2/11.
 */

public class HelpCenterActivity extends Activity implements View.OnClickListener {
    RelativeLayout rl_feedback, rl_help_service_protrocol;
    ImageView iv_helpcenter_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__helpcenter);
        rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
        rl_help_service_protrocol = (RelativeLayout) findViewById(R.id.rl_help_service_protrocol);
        iv_helpcenter_back = (ImageView) findViewById(R.id.iv_helpcenter_back);
        rl_feedback.setOnClickListener(this);
        rl_help_service_protrocol.setOnClickListener(this);
        iv_helpcenter_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feedback:
                Intent intent = new Intent(HelpCenterActivity.this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_helpcenter_back:
                finish();
                break;
            case R.id.rl_help_service_protrocol:
                Intent intent2 = new Intent(HelpCenterActivity.this, ServiceProtocolActivity.class);
                startActivity(intent2);
        }
    }
}
