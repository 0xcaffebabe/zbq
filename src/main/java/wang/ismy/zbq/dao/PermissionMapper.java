package wang.ismy.zbq.dao;

import wang.ismy.zbq.model.entity.user.UserPermission;

public interface PermissionMapper {

    int insertPermission(UserPermission userPermission);

    UserPermission selectById(Integer id);
}
