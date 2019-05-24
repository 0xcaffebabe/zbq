package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.State;

import java.util.List;

/**
 * @author my
 */
public interface StateMapper {

    /**
     * 新增一条动态
     *
     * @param state 动态实体
     * @return 受影响行数
     */
    int insertNew(State state);

    List<State> selectStateByUserIdBatchPaging(@Param("list") List<Integer> list, @Param("page") Page page);

    int countStateByUserId(Integer userId);

    State selectByPrimaryKey(Integer stateId);

    int setInvisibleByPrimaryKey(Integer stateId);

    /**
     * 根据用户分页查询动态列表（根据时间降序）
     *
     * @param userId 用户ID
     * @param page   分页组件
     * @return 动态列表
     */
    List<State> selectStateByUserIdPaging(@Param("user") Integer userId, @Param("page") Page page);

    List<State> selectBatch(List<Integer> stateIdList);
}
