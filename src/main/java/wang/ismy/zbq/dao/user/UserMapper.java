package wang.ismy.zbq.dao.user;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.user.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectByUsername(String username);

    List<User> selectByNickNamePaging(@Param("nickName") String nickName, @Param("page")Page page);

    int insert(User user);

    User selectByPrimaryKey(Integer userId);

    List<User> selectByUserIdBatch(List<Integer> list);

    void updateLastLogin(Integer userId);

    int update(User user);

    /**
    * 查询用户数
    * @return 用户总数
    */
    long count();
}
