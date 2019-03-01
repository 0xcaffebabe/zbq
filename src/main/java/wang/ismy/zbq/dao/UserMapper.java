package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wang.ismy.zbq.entity.Friend;
import wang.ismy.zbq.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectByUsername(String username);

    List<User> selectByNickName(String username);

    int insert(User user);

    User selectByPrimaryKey(Integer userId);

    List<User> selectByUserIdBatch(List<Integer> list);
}
