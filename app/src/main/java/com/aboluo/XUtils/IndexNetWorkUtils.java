package com.aboluo.XUtils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by CJ on 2016/11/27.
 */

public class IndexNetWorkUtils {
    private RequestQueue requestQueue;
    private String URL;
    private String ImageURL;
    private String APPToken;
    private static Context mcontext;
    private static Picasso picasso;
    public   IndexNetWorkUtils(Context context)
    {
        this.mcontext =context;
        requestQueue = MyApplication.getRequestQueue();
        ImageURL = CommonUtils.GetValueByKey(context, "ImgUrl");
        URL = CommonUtils.GetValueByKey(context, "apiurl");
        APPToken = CommonUtils.GetValueByKey(context, "APPToken");
        picasso = Picasso.with(context);
    }
}
