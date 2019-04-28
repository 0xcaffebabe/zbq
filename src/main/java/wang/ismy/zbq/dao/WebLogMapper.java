package wang.ismy.zbq.dao;

import wang.ismy.zbq.model.entity.WebLog;

import java.util.List;

/**
 * @author my
 */
public interface WebLogMapper {

    /**
     * 插入一条web访问记录
     *
     * @param webLog 日志实体
     * @return 受影响条数
     */
    int insertNew(WebLog webLog);

    /**
     * 批量插入web访问记录
     *
     * @param webLogList 日志实体列表
     * @return 受影响条数
     */
    int insertNewBatch(List<WebLog> webLogList);
}
