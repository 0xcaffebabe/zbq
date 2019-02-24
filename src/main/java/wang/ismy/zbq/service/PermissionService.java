package wang.ismy.zbq.service;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.PermissionMapper;
import wang.ismy.zbq.entity.Permission;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /*
    * 返回主键ID
    */

    public int insertPermission(Permission permission){
        permissionMapper.insertPermission(permission);
        return permission.getPermissionId();
    }
}
