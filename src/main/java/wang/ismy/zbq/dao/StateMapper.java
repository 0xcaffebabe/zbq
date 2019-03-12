package wang.ismy.zbq.dao;

import wang.ismy.zbq.entity.State;

import java.util.List;

public interface StateMapper {

    int insertNew(State state);

    List<State> selectStateByUserIdBatch(List<Integer> list);
}
