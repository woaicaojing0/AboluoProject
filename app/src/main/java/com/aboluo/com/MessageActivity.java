package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aboluo.XUtils.DataHelper;
import com.aboluo.adapter.MessageAdapter;
import com.aboluo.model.MessageBean;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * 接收极光推送的消息
 * Created by CJ on 2016/12/26.
 */

public class MessageActivity extends Activity {

    private PullToRefreshListView message_listview;
    private MessageAdapter messageAdapter;
    private ImageView my_info_text_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message_listview = (PullToRefreshListView) findViewById(R.id.message_listview);
        my_info_text_back = (ImageView) findViewById(R.id.my_info_text_back);
        DataHelper dataHelper = new DataHelper(this);
        List<MessageBean> list = dataHelper.GetUserList();
        if (list.size() == 0) {
            Toast.makeText(this, "暂无推送消息", Toast.LENGTH_SHORT).show();
        } else {
            messageAdapter = new MessageAdapter(list, this);
            message_listview.setAdapter(messageAdapter);
        }

        my_info_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
