package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.UserPermission;

public interface PermissionMapper {

    int insertPermission(UserPermission userPermission);

    UserPermission selectById(Integer id);
}
