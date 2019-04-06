package wang.ismy.zbq.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.dao.ContentMapper;
import wang.ismy.zbq.dto.ContentCommentDTO;
import wang.ismy.zbq.dto.ContentDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Comment;
import wang.ismy.zbq.entity.Content;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.CommentVO;
import wang.ismy.zbq.vo.ContentVO;
import wang.ismy.zbq.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

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
            UserVO userVO = new UserVO();
            userVO.setUserId(i.getUser().getUserId());
            vo.setUser(userVO);
            contentVOList.add(vo);
        }

        addContentLikes(contentVOList);
        addContentCommentCount(contentVOList);
        addContentUser(contentVOList);
        return contentVOList;

    }

    private void addContentUser(List<ContentVO> contentVOList) {

        //var userIdList = getUserIdList(contentVOList);

        var userIdList = contentVOList.stream()
                .map(x->x.getUser().getUserId())
                .collect(Collectors.toList());
        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : contentVOList){
            for (var j : userList){
                if (i.getUser().getUserId().equals(j.getUserId())){
                    UserVO userVO = UserVO.convert(j);
                    i.setUser(userVO);
                    break;
                }
            }
        }
    }

    private void addContentCommentCount(List<ContentVO> contentVOList) {

        var list = contentVOList.stream()
                .map(ContentVO::getContentId)
                .collect(Collectors.toList());

        var map = commentService.selectCommentCountByCommentTypeAndTopicIdBatch(CommentTypeEnum.CONTENT,list);

        for (var i : contentVOList){
            if (map.get(i.getContentId()) != null){
                i.setCommentCount(map.get(i.getContentId()));
            }
        }
    }

    public int createCurrentUserStateComment(ContentCommentDTO contentCommentDTO){

        User currentUser = userService.getCurrentUser();
        Comment comment = new Comment();
        comment.setCommentType(CommentTypeEnum.CONTENT.getCode());
        comment.setContent(contentCommentDTO.getContent());
        comment.setFromUser(currentUser);
        comment.setTopicId(contentCommentDTO.getContentId());
        comment.setToUser(userService.selectByPrimaryKey(contentCommentDTO.getToUser()));

        return commentService.createNewCommentRecord(comment);
    }

    public List<CommentVO> selectContentCommentListPaging(Integer contentId, Page p) {
        var list = commentService.selectCommentByCommentTypeAndContentIdPaging(CommentTypeEnum.CONTENT,contentId,p);

        List<CommentVO> commentVOList = new ArrayList<>();
        for (var i : list){
            commentVOList.add(CommentVO.convert(i));
        }

        return commentVOList;

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

    private List<Integer> getUserIdList(List<ContentVO> contentVOList){
        List<Integer> ret = new ArrayList<>();

        for (var i : contentVOList){
            ret.add(i.getUser().getUserId());
        }
        return ret;
    }


    public ContentVO selectByPrimaryKey(Integer contentId) {
        Content content = contentMapper.selectByPrimaryKey(contentId);

        User user = userService.selectByPrimaryKey(content.getUser().getUserId());

        if (user == null){
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }
        UserVO userVO = UserVO.convert(user);

        ContentVO contentVO = new ContentVO();
        BeanUtils.copyProperties(content,contentVO);
        contentVO.setUser(userVO);
        return contentVO;
    }
}
