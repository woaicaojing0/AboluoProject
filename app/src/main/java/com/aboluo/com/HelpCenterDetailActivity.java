package com.aboluo.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by CJ on 2017/2/18.
 */

public class HelpCenterDetailActivity extends Activity {
    WebView webView;
    TextView helpcenterdetail_text_ttitle;
    ImageView helpcenterdetail_text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpcenterdetail);
        webView = (WebView) findViewById(R.id.wb_helpcenterdetail);
        helpcenterdetail_text_back = (ImageView) findViewById(R.id.helpcenterdetail_text_back);
        helpcenterdetail_text_ttitle = (TextView) findViewById(R.id.helpcenterdetail_text_ttitle);
        Intent intent = getIntent();
        String url = intent.getStringExtra("helpcenterURl");
        String helpcentertitle = intent.getStringExtra("helpcentertitle");
        Log.i("helpcenterurl", url);
        if (url != null && helpcentertitle != null) {
            helpcenterdetail_text_ttitle.setText(helpcentertitle);
            webView.loadUrl(url);
        } else {
        }
        helpcenterdetail_text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
