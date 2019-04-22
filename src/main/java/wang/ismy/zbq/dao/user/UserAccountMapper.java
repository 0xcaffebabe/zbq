package wang.ismy.zbq.dao.user;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.user.UserAccount;

import java.util.List;

/**
 * @author my
 */
public interface UserAccountMapper {

    /**
     * 插入一条用户第三方账户绑定记录
     *
     * @param account 第三方账户绑定关系实体
     * @return 受影响行数
     */
    int insertNew(UserAccount account);

    /**
    * 根据账户类型和账户名查询出对应的用户绑定关系
     * @param accountType 账户类型
     * @param accountName 账户名
     * @return 用户账户绑定实体
    *
    */
    UserAccount selectByAccountTypeAndAccountName(@Param("accountType") Integer accountType,@Param("accountName") String accountName);

    UserAccount selectByAccountTypeAndUserId(@Param("accountType") Integer accountType,@Param("user") Integer userId);

    int updateByAccountNameByAccountTypeAndUserId(@Param("accountType") Integer accountType,@Param("user") Integer userId,@Param("accountName") String accountName);

    List<UserAccount> selectByUser(Integer userId);
}
