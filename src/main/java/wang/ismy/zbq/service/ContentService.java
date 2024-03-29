package wang.ismy.zbq.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.dao.ContentMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.dto.content.ContentCommentDTO;
import wang.ismy.zbq.model.dto.content.ContentDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.Subscription;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.enums.PermissionEnum;
import wang.ismy.zbq.model.vo.user.AuthorVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.CommentVO;
import wang.ismy.zbq.model.vo.ContentVO;
import wang.ismy.zbq.model.vo.user.UserVO;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
@Setter(onMethod_ = @Inject)
public class ContentService {

    private ContentMapper mapper;

    private UserService userService;

    private LikeService likeService;

    private CommentService commentService;

    private CollectionService collectionService;

    private ExecuteService executeService;

    private SubscriptionService subscriptionService;

    private InformService informService;

    /**
     * 以当前登录用户身份发布内容，需要有PUBLISH_CONTENT权限
     *
     * @param contentDTO 内容数据传输对象
     */
    @Permission(PermissionEnum.PUBLISH_CONTENT)
    public void publishContent(ContentDTO contentDTO) {
        var currentUser = userService.getCurrentUser();
        Content content = new Content();
        BeanUtils.copyProperties(contentDTO, content);
        content.setUser(currentUser);
        if (mapper.insertNew(content) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

        executeService.submit(()-> informService.informUserContent(currentUser.getUserId(),contentDTO.getTitle()));

    }

    /**
     * 拉取内容列表
     *
     * @param page 分页组件
     * @return 内容视图列表
     */
    public List<ContentVO> pullContents(Page page) {
        long time = System.currentTimeMillis();
        var contentList = mapper.selectContentListPaging(page);

        List<ContentVO> contentVOList = new ArrayList<>();
        for (var i : contentList) {
            ContentVO vo = new ContentVO();
            BeanUtils.copyProperties(i, vo);
            vo.setUser(AuthorVO.of(i.getUser().getUserId()));
            contentVOList.add(vo);
        }

        var currentUser = userService.getCurrentUser();

        var task1 = executeService.submit(() -> addContentLikes(contentVOList, currentUser));
        var task2 = executeService.submit(() -> addContentCommentCount(contentVOList));
        var task3 = executeService.submit(() -> addContentUser(contentVOList, currentUser));
        var task4 = executeService.submit(() -> addContentCollection(contentVOList, currentUser));

        try {
            task1.get();
            task2.get();
            task3.get();
            task4.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.warn("获取内容列表，耗时:{}", System.currentTimeMillis() - time);
        return contentVOList;

    }

    public void currentUserCollectContent(CollectionDTO collectionDTO) {

        if (collectionService.selectByTypeAndContentId(CollectionTypeEnum.CONTENT,
                collectionDTO.getContentId(),
                userService.getCurrentUser().getUserId()) != null) {
            ErrorUtils.error(R.COLLECT_EXIST);
        }

        if (collectionDTO.getCollectionType()
                .equals(CollectionTypeEnum.CONTENT.getCode())) {
            if (collectionService.currentUserAddCollection(collectionDTO) != 1) {
                ErrorUtils.error(R.COLLECT_FAIL);
            }

        } else {
            ErrorUtils.error(R.TYPE_NOT_MATCH);
        }
    }

    public int createCurrentUserStateComment(ContentCommentDTO contentCommentDTO) {

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
        var list = commentService.selectComments(CommentTypeEnum.CONTENT, contentId, p);

        List<CommentVO> commentVOList = new ArrayList<>();
        for (var i : list) {
            commentVOList.add(CommentVO.convert(i));
        }

        addContentCommentUser(commentVOList);

        return commentVOList;

    }


    public ContentVO selectByPrimaryKey(Integer contentId) {
        Content content = mapper.selectByPrimaryKey(contentId);

        User user = userService.selectByPrimaryKey(content.getUser().getUserId());

        if (user == null) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
        AuthorVO authorVO = AuthorVO.convert(user);

        ContentVO contentVO = new ContentVO();
        BeanUtils.copyProperties(content, contentVO);
        contentVO.setUser(authorVO);
        return contentVO;
    }

    public Content selectRaw(Integer contentId) {
        return mapper.selectByPrimaryKey(contentId);
    }

    public Map<Integer, String> selectTitleBatch(List<Integer> contentIdList) {
        if (contentIdList == null || contentIdList.size() == 0) {
            return Map.of();
        }

        var list = mapper.selectBatch(contentIdList);

        Map<Integer, String> map = new HashMap<>();
        for (var i : list) {
            map.put(i.getContentId(), i.getTitle());
        }

        return map;
    }

    /**
     * 根据用户ID分页查询
     *
     * @param userId 用户ID
     * @param page   分页组件
     * @return 内容列表
     */
    public List<Content> select(Integer userId, Page page) {

        return mapper.selectByUserIdPaging(userId, page);
    }

    public List<Content> selectBatch(List<Integer> contentIdList) {
        if (contentIdList == null || contentIdList.size() == 0) {
            return List.of();
        }
        return mapper.selectBatch(contentIdList);
    }

    private void addContentCollection(List<ContentVO> contentVOList, User currentUser) {
        var contentIdList = contentVOList.stream()
                .map(ContentVO::getContentId)
                .collect(Collectors.toList());

        var map = collectionService.selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT,
                contentIdList, currentUser.getUserId());

        for (var i : contentVOList) {

            var collectionCount = map.get(i.getContentId());

            if (collectionCount != null) {
                i.setCollectCount(collectionCount.getCollectionCount());
                i.setHasCollect(collectionCount.getHasCollect());
            } else {
                i.setCollectCount(0L);
                i.setHasCollect(false);
            }

        }
    }


    private void addContentUser(List<ContentVO> contentVOList, User currentUser) {
        var userIdList = contentVOList.stream()
                .map(x -> x.getUser().getUserId())
                .collect(Collectors.toList());
        var userList = userService.selectByUserIdBatch(userIdList);

        var subscriptionList = subscriptionService.selectBatch(userIdList, currentUser.getUserId());

        Map<Integer, Subscription> subscriptionMap = new HashMap<>();
        for (var i : subscriptionList) {
            subscriptionMap.put(i.getAuthor().getUserId(), i);
        }
        for (var i : contentVOList) {
            for (var j : userList) {
                if (i.getUser().getUserId().equals(j.getUserId())) {
                    AuthorVO authorVO = AuthorVO.convert(j);
                    authorVO.setAttention(subscriptionMap.get(authorVO.getUserId()) != null);

                    i.setUser(authorVO);
                    break;
                }
            }
        }
    }

    private void addContentCommentCount(List<ContentVO> contentVOList) {

        var list = contentVOList.stream()
                .map(ContentVO::getContentId)
                .collect(Collectors.toList());

        var map = commentService.selectCommentCount(CommentTypeEnum.CONTENT, list);

        for (var i : contentVOList) {
            if (map.get(i.getContentId()) != null) {
                i.setCommentCount(map.get(i.getContentId()));
            }
        }
    }


    private void addContentCommentUser(List<CommentVO> commentVOList) {

        List<Integer> userIdList = new ArrayList<>();
        Map<Integer, UserVO> userVOMap = new HashMap<>();
        for (var i : commentVOList) {
            if (!userIdList.contains(i.getFromUser().getUserId())) {
                userIdList.add(i.getFromUser().getUserId());
            }

            if (i.getToUser() != null) {
                if (!userIdList.contains(i.getToUser().getUserId())) {
                    userIdList.add(i.getToUser().getUserId());
                }
            }
        }

        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList) {
            userVOMap.put(i.getUserId(), UserVO.convert(i));
        }

        for (var i : commentVOList) {
            i.setFromUser(userVOMap.get(i.getFromUser().getUserId()));

            if (i.getToUser() != null) {
                i.setToUser(userVOMap.get(i.getToUser().getUserId()));
            }
        }
    }

    private void addContentLikes(List<ContentVO> contentVOList, User currentUser) {
        List<Integer> contentIdList = new ArrayList<>();

        for (var i : contentVOList) {
            contentIdList.add(i.getContentId());
        }

        Map<Integer, Long> contentLikeCount =
                likeService.countLikeBatch(LikeTypeEnum.CONTENT, contentIdList);

        var hasLikeMap =
                likeService.hasLikeBatch(
                        LikeTypeEnum.CONTENT, contentIdList, currentUser.getUserId());
        for (var i : contentVOList) {
            i.setLikeCount(contentLikeCount.get(i.getContentId()));
            if (hasLikeMap.get(i.getContentId()) != null) {
                i.setHasLike(hasLikeMap.get(i.getContentId()));
            } else {
                i.setHasLike(false);
            }
        }


    }

    private List<Integer> getUserIdList(List<ContentVO> contentVOList) {
        List<Integer> ret = new ArrayList<>();

        for (var i : contentVOList) {
            ret.add(i.getUser().getUserId());
        }
        return ret;
    }


}
