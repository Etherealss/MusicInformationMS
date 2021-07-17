package pers.wtk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wtk
 * @description 特殊字符检测工具（防止传入非法字符和sql注入攻击）
 * @date 2021-06-13
 */
public class IllegalStrFilterUtil {
    private static final Logger Logger = LoggerFactory.getLogger(IllegalStrFilterUtil.class);

    /** 特殊字符 */
    private static final String REGX = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§ ";

    private static final String[] SQL_STR = {
            "DELETE", "ASCII", "UPDATE", "SELECT", "'", "SUBSTR(", "COUNT(", " OR ", " AND ", "DROP",
            "EXECUTE", "EXEC", "TRUNCATE", "INTO", "DECLARE", "MASTER",
    };

    /**
     * 对常见的sql注入攻击进行拦截
     * @param input
     * @return true 表示参数不存在SQL注入风险
     * false 表示参数存在SQL注入风险
     */
    public static Boolean sqlStrFilter(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.toUpperCase();
        boolean contain = false;
        for (String sqlword : SQL_STR) {
            contain = contain || input.contains(sqlword);
        }
//        boolean contain = input.contains("DELETE") || input.contains("ASCII") ||
//                input.contains("UPDATE") || input.contains("SELECT") ||
//                input.contains("'") || input.contains("SUBSTR(") ||
//                input.contains("COUNT(") || input.contains(" OR ") ||
//                input.contains(" AND ") || input.contains("DROP") ||
//                input.contains("EXECUTE") || input.contains("EXEC") ||
//                input.contains("TRUNCATE") || input.contains("INTO") ||
//                input.contains("DECLARE") || input.contains("MASTER");
        if (contain) {
            Logger.error("该参数有SQL注入风险，输入参数为：" + input);
            return false;
        }
        Logger.debug("通过sql检测");
        return true;
    }

    /**
     * 对非法字符进行检测
     * @param input
     * @return true 表示参数不包含非法字符
     * false 表示参数包含非法字符
     */
    public static Boolean isIllegalStr(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.trim();
        Pattern compile = Pattern.compile(REGX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(input);
        Logger.debug("通过字符串检测");
        return matcher.find();
    }
}
