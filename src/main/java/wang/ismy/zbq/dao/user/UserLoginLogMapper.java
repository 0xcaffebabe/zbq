package wang.ismy.zbq.dao.user;

import wang.ismy.zbq.model.entity.user.UserLoginLog;

import java.util.List;

public interface UserLoginLogMapper {

    /**
     * 插入一条登录记录
     *
     * @param log 登录日志实体
     * @return 受影响行数
     */
    int insertNew(UserLoginLog log);

    /**
     * 根据用户ID查询出该用户最近10条登录记录
     *
     * @param userId 用户ID
     * @return 登录日志列表
     */
    List<UserLoginLog> selectTop10ByUserId(Integer userId);

    /**
     * 查询产生登录日志(按天分组)的数量
     *
     * @param userId 用户ID
     * @return 数量
     */
    long countLogByUserId(Integer userId);

}
