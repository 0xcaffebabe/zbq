package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.user.UserPermission;

public interface PermissionMapper {

    int insertPermission(UserPermission userPermission);

    UserPermission selectById(Integer id);
}
