package com.aboluo.XUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aboluo.com.R;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;

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
    public static String GetValueByKey(Context context, String key) {
        Properties props = new Properties();
        String result = "";
        try {
            InputStream in = context.getResources().openRawResource(R.raw.url);
            props.load(in);
            String value = props.getProperty(key);
            Log.i("woaicoajing", key + "键的值是：" + value);
            return value;
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
     * 功能描述：金额字符串转换：单位元转成单分
     *
     * @param //传入需要转换的金额字符串
     * @return 转换后的金额字符串
     */
    public static String yuanToFen(Object o) {
        if (o == null)
            return "0";
        String s = o.toString();
        int posIndex = -1;
        String str = "";
        StringBuilder sb = new StringBuilder();
        if (s != null && s.trim().length() > 0 && !s.equalsIgnoreCase("null")) {
            posIndex = s.indexOf(".");
            if (posIndex > 0) {
                int len = s.length();
                if (len == posIndex + 1) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append("00");
                } else if (len == posIndex + 2) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 2)).append("0");
                } else if (len == posIndex + 3) {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                } else {
                    str = s.substring(0, posIndex);
                    if (str == "0") {
                        str = "";
                    }
                    sb.append(str).append(s.substring(posIndex + 1, posIndex + 3));
                }
            } else {
                sb.append(s).append("00");
            }
        } else {
            sb.append("0");
        }
        str = removeZero(sb.toString());
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     * 功能描述：去除字符串首部为"0"字符
     *
     * @param str 传入需要转换的字符串
     * @return 转换后的字符串
     */
    public static String removeZero(String str) {
        char ch;
        String result = "";
        if (str != null && str.trim().length() > 0 && !str.trim().equalsIgnoreCase("null")) {
            try {
                for (int i = 0; i < str.length(); i++) {
                    ch = str.charAt(i);
                    if (ch != '0') {
                        result = str.substring(i);
                        break;
                    }
                }
            } catch (Exception e) {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }

    public static boolean IsLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE);
        String memberId = sharedPreferences.getString("UserId", "0");
        if (memberId.equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean LoginOut(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE).edit();
        editor.remove("UserName");
        editor.remove("UserPwd");
        editor.remove("UserId");
        return editor.commit();
    }

    public static boolean Login(Context context, String UserName, String UserPwd, String UserId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE).edit();
        editor.putString("UserName", UserName);
        editor.putString("UserPwd", UserPwd);
        editor.putString("UserId", "18");
        return editor.commit();
    }
    public static boolean LoginImageURl(Context context, String URL) {
        SharedPreferences.Editor editor = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE).edit();
        editor.putString("LoginImageURl", URL);
        return editor.commit();
    }
    public static String GetLoginImageURl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE);
        String LoginImageURl = sharedPreferences.getString("LoginImageURl", "0");
        return LoginImageURl;
    }
    public static String GetMemberId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("aboluoInfo", Context.MODE_PRIVATE);
        String memberId = sharedPreferences.getString("UserId", "0");
        return memberId;
    }

    public static String Getpercent(int num1, int num2) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format((float) num1 / (float) num2 * 100) + "%";

        //System.out.println("num1和num2的百分比为:" + result + "%");
        return result;
    }

    /**
     *
     * @param list 需要转化的list
     * @param separator 分隔符
     * @return
     */
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
