package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.PermissionMapper;
import wang.ismy.zbq.entity.UserPermission;
import wang.ismy.zbq.service.user.UserService;

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

        var permission = permissionMapper.selectById(currentUser.getUserPermission().getUserPermissionId());

        if (permission == null){
            UserPermission userPermission = new UserPermission();
            insertPermission(userPermission);
            currentUser.setUserPermission(userPermission);
            userService.update(currentUser);
            return userPermission;
        }else{
            return permission;
        }

    }
}
