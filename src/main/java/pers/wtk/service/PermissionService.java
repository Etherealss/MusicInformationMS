package pers.wtk.service;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-20
 */
public interface PermissionService {

    /**
     * 获取所有权限
     * @return
     * @throws
     */
    List<String> getAllPermissions() throws Exception;

    /**
     * 更新用户权限
     * @param userId
     * @param addPerm
     * @param removePerm
     * @throws Exception
     */
    void updateUserPermission(long userId, List<String> addPerm, List<String> removePerm) throws Exception;
}
