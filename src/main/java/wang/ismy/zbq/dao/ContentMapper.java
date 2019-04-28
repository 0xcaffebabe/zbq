package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Content;

import java.util.List;

public interface ContentMapper {

    int insertNew(Content content);

    List<Content> selectContentListPaging(@Param("page")Page page);

    Content selectByPrimaryKey(Integer contentId);

}
