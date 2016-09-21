package com.aboluo.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by CJ on 2016/9/21.
 */

public class RetrievepwdActivity  extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.retrievepwd);
    }
}
