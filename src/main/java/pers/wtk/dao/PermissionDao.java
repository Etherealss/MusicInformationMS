package pers.wtk.dao;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-20
 */
public interface PermissionDao {

    /**
     * 获取所有权限
     * @return
     */
    List<String> getAllPermission();
}
