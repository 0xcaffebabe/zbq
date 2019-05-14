package wang.ismy.zbq.dao;

import wang.ismy.zbq.model.entity.VideoSearchLog;

/**
 * @author my
 */
public interface VideoSearchLogMapper {

    /**
     * 新增一条视频搜索记录
     *
     * @param log 记录实体
     * @return 受影响行数
     */
    int insertNew(VideoSearchLog log);
}
