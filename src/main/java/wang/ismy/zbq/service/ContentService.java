package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.dao.ContentMapper;
import wang.ismy.zbq.dto.ContentDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Content;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.ContentVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Permission(PermissionEnum.PUBLISH_CONTENT)
    public void currentUserPublish(ContentDTO contentDTO){
        var currentUser = userService.getCurrentUser();
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO,content);
        content.setUser(currentUser);
        if (contentMapper.insertNew(content) != 1) ErrorUtils.error(StringResources.UNKNOWN_ERROR);
    }

    public List<ContentVO> selectContentListPaging(Page page){
        var contentList = contentMapper.selectContentListPaging(page);

         List<ContentVO> contentVOList = new ArrayList<>();
        for (var i : contentList){
            ContentVO vo = new ContentVO();
            BeanUtils.copyProperties(i,vo);
            contentVOList.add(vo);
        }

        addContentLikes(contentVOList);

        return contentVOList;

    }

    private void addContentLikes(List<ContentVO> contentVOList) {

        var currentUser = userService.getCurrentUser();
        List<Integer> contentIdList = new ArrayList<>();

        for (var i : contentVOList){
            contentIdList.add(i.getContentId());
        }

        Map<Integer,Long> contentLikeCount = likeService.countLikeByLikeTypeAndContentIdBatch(LikeTypeEnum.CONTENT,contentIdList);

        var hasLikeMap =
                likeService.selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(
                        LikeTypeEnum.CONTENT,contentIdList,currentUser.getUserId());
        for (var i : contentVOList){
            i.setLikeCount(contentLikeCount.get(i.getContentId()));
            if (hasLikeMap.get(i.getContentId()) != null){
                i.setHasLike(hasLikeMap.get(i.getContentId()));
            }else{
                i.setHasLike(false);
            }
        }


    }
}
