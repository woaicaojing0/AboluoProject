package com.aboluo.XUtils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cj34920 on 2016/10/11.
 */

public class ValidateUtils {
    /**
     * 从短信字符窜提取验证码
     *
     * @param body      短信内容
     * @param YZMLENGTH 验证码的长度 一般6位或者4位
     * @return 接取出来的验证码
     */
    public static String getyzm(String body, int YZMLENGTH) {
        // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现
        Pattern p = Pattern.compile("(?<![0-9])([0-9]{" + YZMLENGTH + "})(?![0-9])");
        Matcher m = p.matcher(body);
        if (m.find()) {
            System.out.println(m.group());
            return m.group(0);
        }
        return null;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证密码长度和是否包含数字
     */
    public static boolean isMiMaRight(String mobiles) {
        String telRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证密码长度和是否包含数字
     */
    public static boolean Zipcode(String zipcode) {
        String telRegex = "^\\d{6}$";
        if (TextUtils.isEmpty(zipcode)) return false;
        else return zipcode.matches(telRegex);
    }
    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
