package pers.wtk.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description 音乐听歌权限
 * @date 2021-06-12
 */
public class MusicCharge {

    /** 免费 */
    public static final int CHARGE = 0;
    /** 免费 */
    public static final int FREE = 1;
    /** 会员免费*/
    public static final int MEMBER = 2;

    /**
     * 说明文字字典
     */
    private static final Map<Integer, String> PERMISS_MSG_DIC = new HashMap<>(3);

    static {
        PERMISS_MSG_DIC.put(CHARGE, "付费");
        PERMISS_MSG_DIC.put(FREE, "免费");
        PERMISS_MSG_DIC.put(MEMBER, "会员免费");
    }

    public static Map<Integer, String> getPermissMsgDic() {
        // 深拷贝
        return new HashMap<>(PERMISS_MSG_DIC);
    }

    /**
     * @param permission
     * @return 听歌权限的说明文本
     */
    public static String msgOf(int permission) {
        return PERMISS_MSG_DIC.get(permission);
    }
}
