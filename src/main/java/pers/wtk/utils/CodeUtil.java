package pers.wtk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wtk
 * @description 编码解码
 * @date 2021-06-16
 */
public class CodeUtil {

    /**
     * decodeUrlConponent解码
     * @param s
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodeUrlConponent(String s) throws UnsupportedEncodingException {
        if (s != null && s.length() > 0) {
            return URLDecoder.decode(s, "UTF-8");
        } else {
            return s;
        }
    }
}
