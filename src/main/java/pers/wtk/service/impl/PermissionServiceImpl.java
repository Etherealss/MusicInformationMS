package pers.wtk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wtk.dao.AdminDao;
import pers.wtk.dao.PermissionDao;
import pers.wtk.service.PermissionService;

import java.util.List;

/**
 * @author wtk
 * @description
 * @date 2021-06-20
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private AdminDao adminDao;

    @Override
    public List<String> getAllPermissions() throws Exception {
        return permissionDao.getAllPermission();
    }

    @Override
    public void updateUserPermission(long userId, List<String> addPerm, List<String> removePerm) throws Exception {
        for (String perm : addPerm) {
            adminDao.addUserPremission(userId, perm);
        }
        for (String perm : removePerm) {
            adminDao.deleteUserPremission(userId, perm);
        }
    }

}
