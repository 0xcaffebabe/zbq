package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Friend;
import wang.ismy.zbq.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectByUsername(String username);

    List<User> selectByNickNamePaging(@Param("nickName") String nickName, @Param("page")Page page);

    int insert(User user);

    User selectByPrimaryKey(Integer userId);

    List<User> selectByUserIdBatch(List<Integer> list);
}
