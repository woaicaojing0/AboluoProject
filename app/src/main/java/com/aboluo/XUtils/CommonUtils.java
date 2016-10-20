package com.aboluo.XUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.aboluo.com.R;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CJ on 2016/10/2.
 */

public class CommonUtils {


    public static String getMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }
    //从resources中的raw 文件夹中获取文件并读取数据
    public static String GetValueByKey(Context  context,String key){
        Properties props = new Properties();
        String result = "";
        try {
            InputStream in = context.getResources().openRawResource(R.raw.url);
            props.load(in);
            String value = props.getProperty(key);
            Log.i("woaicoajing",key +"键的值是："+ value);
            return  value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
    * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
