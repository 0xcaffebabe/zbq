package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.State;

import java.util.List;

/**
 * @author my
 */
public interface StateMapper {

    int insertNew(State state);

    List<State> selectStateByUserIdBatchPaging(@Param("list") List<Integer> list, @Param("page")Page page);

    int countStateByUserId(Integer userId);

    State selectByPrimaryKey(Integer stateId);

    int setInvisibleByPrimaryKey(Integer stateId);

}
