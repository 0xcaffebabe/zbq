package wang.ismy.zbq.dao;

import lombok.Data;
import wang.ismy.zbq.entity.Broadcast;

import java.util.List;


/**
 * @author my
 */
public interface BroadcastMapper {

    /**
     * 插入一条广播聊天消息
     *
     * @param broadcast 广播聊天实体
     * @return 受影响行数
     */
    int insertNew(Broadcast broadcast);

    /**
     * 查询最新10条广播
     *
     * @return 广播消息列表
     */
    List<Broadcast> selectNewest10();

}
