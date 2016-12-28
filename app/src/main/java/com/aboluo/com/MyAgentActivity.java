package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aboluo.com.WebActivity.AgentDetailActivity;


/**
 * Created by cj34920 on 2016/12/28.
 * 我的代理包括一级代理、二级代理、三级代理
 */

public class MyAgentActivity extends Activity implements View.OnClickListener {
    private LinearLayout agent_one, agent_two, agent_three;
    private ImageView agent_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_agent);
        init();
        agent_back.setOnClickListener(this);
        agent_one.setOnClickListener(this);
        agent_two.setOnClickListener(this);
        agent_three.setOnClickListener(this);
    }

    private void init() {
        agent_one = (LinearLayout) findViewById(R.id.agent_one);
        agent_two = (LinearLayout) findViewById(R.id.agent_two);
        agent_three = (LinearLayout) findViewById(R.id.agent_three);
        agent_back = (ImageView) findViewById(R.id.agent_back);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyAgentActivity.this, AgentDetailActivity.class);
        switch (v.getId()) {
            case R.id.agent_one:
                intent.putExtra("AgentDetailLevel", 1);
                startActivity(intent);
                break;
            case R.id.agent_two:
                intent.putExtra("AgentDetailLevel", 2);
                startActivity(intent);
                break;
            case R.id.agent_three:
                intent.putExtra("AgentDetailLevel", 3);
                startActivity(intent);
                break;
            case R.id.agent_back:
                finish();
                break;
        }
    }
}
