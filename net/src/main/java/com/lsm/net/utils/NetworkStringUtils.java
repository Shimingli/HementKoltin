package com.lsm.net.utils;


import android.text.TextUtils;

/**
 * Created by shiming on 2017/4/3.
 */
public class NetworkStringUtils {

    /**
     * 判断字符是否为null或空串.
     *
     * @param src 待判断的字符
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || "".equals(src.trim())
                || "null".equalsIgnoreCase(src);
    }

    /**
     * 截取字符串前缀
     * @return
     */
    public static String getStrPrefix(String str, int length){
        String strPrefix = "";
        if (TextUtils.isEmpty(str)){
            return str;
        }
        if (length < str.length()){
            strPrefix = str.substring(0, length);
        }else {
            return str;
        }
        return strPrefix;
    }

}
