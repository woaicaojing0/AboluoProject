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
    /**
     *
     * 功能描述：金额字符串转换：单位元转成单分

     * @param //传入需要转换的金额字符串
     * @return 转换后的金额字符串
     */
    public static String yuanToFen(Object o) {
        if(o == null)
            return "0";
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length()>0 && !s.equalsIgnoreCase("null")){
            posIndex = s.indexOf(".");
            if(posIndex>0){
                int len = s.length();
                if(len == posIndex+1){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append("00");
                }else if(len == posIndex+2){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex+1,posIndex+2)).append("0");
                }else if(len == posIndex+3){
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex+1,posIndex+3));
                }else{
                    str = s.substring(0,posIndex);
                    if(str == "0"){
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex+1,posIndex+3));
                }
            }else{
                sb.append(s).append("00");
            }
        }else{
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
            return str;
        }else{
            return "0";
        }
    }
    /**
     *
     * 功能描述：去除字符串首部为"0"字符

     * @param str 传入需要转换的字符串
     * @return 转换后的字符串
     */
    public static String removeZero(String str){
        char  ch;
        String result = "";
        if(str != null && str.trim().length()>0 && !str.trim().equalsIgnoreCase("null")){
            try{
                for(int i=0;i<str.length();i++){
                    ch = str.charAt(i);
                    if(ch != '0'){
                        result = str.substring(i);
                        break;
                    }
                }
            }catch(Exception e){
                result = "";
            }
        }else{
            result = "";
        }
        return result;

    }
}
