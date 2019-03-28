package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.PermissionMapper;
import wang.ismy.zbq.entity.UserPermission;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;
    /*
    * 返回主键ID
    */

    public int insertPermission(UserPermission userPermission){
        permissionMapper.insertPermission(userPermission);
        return userPermission.getUserPermissionId();
    }

    public UserPermission selectCurrentUserPermission(){
        var currentUser = userService.getCurrentUser();

        return permissionMapper.selectById(currentUser.getUserPermission().getUserPermissionId());
    }
}
