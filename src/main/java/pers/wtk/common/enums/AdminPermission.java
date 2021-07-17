package pers.wtk.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description 管理员权限
 * @date 2021-05-26
 */
public class AdminPermission {
    public static final String USER = "OperateUser";
    public static final String MUSIC = "OperateMusic";
    public static final String SYSTEM = "System";
    public static final String DATA = "Data";

    private static Map<String, String> adminTypeMap = new HashMap<>(3);

    static {
        adminTypeMap.put(USER, "用户管理员");
        adminTypeMap.put(MUSIC, "音乐管理员");
        adminTypeMap.put(SYSTEM, "系统管理员");
        adminTypeMap.put(DATA, "数据管理员");
    }

    /**
     * 返回管理员类型对应的中文含义
     * @param type 管理员类型
     * @return 管理员类型对应的中文含义，可能为null
     */
    public static String getTypeDescription(String type) {
        return adminTypeMap.get(type);
    }
}
