package pers.wtk.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wtk
 * @description 管理员DAO
 * @date 2021-06-19
 */
public interface AdminDao {

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<String> getUserPremissions(Long userId);

    /**
     * 添加用户权限
     * @param userId
     * @param permission
     * @return
     */
    boolean addUserPremission(@Param("userId") Long userId, @Param("permission") String permission);

    /**
     * 删除
     * @param permission
     * @return
     */
    boolean deleteUserPremission(@Param("userId") Long userId, @Param("permission") String permission);
}
