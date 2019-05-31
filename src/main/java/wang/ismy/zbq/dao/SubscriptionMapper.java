package wang.ismy.zbq.dao;


import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.entity.Subscription;

import java.util.List;

public interface SubscriptionMapper {

    int insertNew(Subscription subscription);

    Subscription selectByUserAndAuthor(@Param("user") Integer user,@Param("author") Integer author);

    List<Subscription> selectByAuthorBatch(@Param("list") List<Integer> authorIdList,
                                           @Param("user") Integer user);

    int deleteByPrimaryKey(Integer id);
}
