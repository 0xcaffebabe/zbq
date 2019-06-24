package wang.ismy.zbq.dao.user;

import wang.ismy.zbq.model.entity.user.UserSetting;

/**
 * @author my
 */
public interface UserSettingMapper {

    /**
     * 插入一条用户设置
     *
     * @param setting 设置实体
     * @return 数据库受影响数
     */
    int insertNew(UserSetting setting);

    /**
     * 根据用户ID查询设置
     *
     * @param user 用户ID
     * @return 设置实体
     */
    UserSetting select(Integer user);

    /**
     * 更新用户设置
     *
     * @param setting 设置实体
     * @return 受影响数
     */
    int update(UserSetting setting);
}
