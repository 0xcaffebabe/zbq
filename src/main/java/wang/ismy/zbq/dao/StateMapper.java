package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.State;

import java.util.List;

public interface StateMapper {

    int insertNew(State state);

    List<State> selectStateByUserIdBatchPaging(@Param("list") List<Integer> list, @Param("page")Page page);

    int countStateByUserId(Integer userId);

}
