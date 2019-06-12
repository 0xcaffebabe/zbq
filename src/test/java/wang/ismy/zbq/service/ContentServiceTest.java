package wang.ismy.zbq.service;


import org.junit.Assume;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.ContentMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.model.dto.CollectionCountDTO;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.dto.content.ContentCommentDTO;
import wang.ismy.zbq.model.dto.content.ContentDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Collection;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.Subscription;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.model.vo.ContentVO;
import wang.ismy.zbq.model.vo.user.AuthorVO;
import wang.ismy.zbq.model.vo.user.UserVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;

import static org.mockito.Mockito.*;

import javax.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("内容服务测试")
public class ContentServiceTest {

    @InjectMocks
    ContentService contentService;

    @Mock
    ContentMapper contentMapper;

    @Mock
    UserService userService;

    @Mock
    ExecuteService executeService;

    @Mock
    LikeService likeService;

    @Mock
    CommentService commentService;

    @Mock
    SubscriptionService subscriptionService;

    @Mock
    CollectionService collectionService;

    @Nested
    class 内容发布测试 {
        /**
         * @see ContentService#publishContent(ContentDTO)
         */
        @Test
        public void 测试发布内容应该成功() {
            when(userService.getCurrentUser()).thenReturn(User.convert(1));
            when(contentMapper.insertNew(any())).thenReturn(1);

            ContentDTO dto = new ContentDTO();
            dto.setContent("测试内容");
            dto.setTags("测试标签");
            dto.setTitle("测试标题");

            contentService.publishContent(dto);

            verify(contentMapper).insertNew(argThat(e ->
                    e.getContent().equals("测试内容") &&
                            e.getTags().equals("测试标签") &&
                            e.getTitle().equals("测试标题")
            ));
        }

        /**
         * @see ContentService#publishContent(ContentDTO)
         */
        @Test
        public void 测试发布内容应该抛出异常() {
            when(userService.getCurrentUser()).thenReturn(User.convert(1));
            when(contentMapper.insertNew(any())).thenReturn(-1);

            ContentDTO dto = new ContentDTO();
            dto.setContent("测试内容");
            dto.setTags("测试标签");
            dto.setTitle("测试标题");

            assertThrows(RuntimeException.class, () -> contentService.publishContent(dto));

            verify(contentMapper).insertNew(argThat(content -> content.getContent().equals("测试内容")
                    && content.getTitle().equals("测试标题")
                    && content.getTags().equals("测试标签")
            ));
        }

        /**
         * @see ContentService#addContentLikes(List, User)
         */
        @Test
        public void 测试拉取内容时添加点赞数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var contentVOList = List.of(
                    ContentVO.builder().contentId(1).build(),
                    ContentVO.builder().contentId(2).build(),
                    ContentVO.builder().contentId(3).build(),
                    ContentVO.builder().contentId(4).build()
            );
            var currentUser = User.convert(1);

            when(likeService.countLikeBatch(eq(LikeTypeEnum.CONTENT), argThat(list ->
                    list.size() == 4
                            && list.containsAll(List.of(1, 2, 3, 4))
            ))).thenReturn(
                    Map.of(1, 1L, 2, 2L, 3, 3L, 4, 4L)
            );
            when(likeService.hasLikeBatch(
                    eq(LikeTypeEnum.CONTENT), argThat(list ->
                            list.size() == 4
                                    && list.containsAll(List.of(1, 2, 3, 4))
                    ), eq(currentUser.getUserId()))).thenReturn(
                    Map.of(1, true, 2, true, 3, false, 4, false)
            );

            var method = contentService.getClass().getDeclaredMethod("addContentLikes", List.class, User.class);
            method.setAccessible(true);

            method.invoke(contentService, contentVOList, currentUser);

            assertEquals(4, contentVOList.size());
            assertEquals(true, contentVOList.get(1).getHasLike());
            assertEquals(false, contentVOList.get(2).getHasLike());

        }

        /**
         * @see ContentService#addContentCommentCount(List)
         */
        @Test
        public void 测试拉取内容时添加评论数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var contentVOList = List.of(
                    ContentVO.builder().contentId(1).build(),
                    ContentVO.builder().contentId(2).build(),
                    ContentVO.builder().contentId(3).build(),
                    ContentVO.builder().contentId(4).build()
            );

            when(commentService.selectCommentCount(eq(CommentTypeEnum.CONTENT), argThat(list ->
                    list.size() == 4 && list.containsAll(List.of(1, 2, 3, 4))
            ))).thenReturn(
                    Map.of(1, 1L, 2, 2L, 3, 3L, 4, 4L)
            );

            var method = contentService.getClass().getDeclaredMethod("addContentCommentCount", List.class);
            method.setAccessible(true);
            method.invoke(contentService, contentVOList);

            verify(commentService).selectCommentCount(eq(CommentTypeEnum.CONTENT), any());

            assertEquals(1L, (long) contentVOList.get(0).getCommentCount());
            assertEquals(4L, (long) contentVOList.get(3).getCommentCount());
        }

        /**
         * @see ContentService#addContentUser(List, User)
         */
        @Test
        public void 测试拉取内容时添加用户数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var contentVOList = List.of(
                    ContentVO.builder().user(AuthorVO.of(1)).build(),
                    ContentVO.builder().user(AuthorVO.of(2)).build(),
                    ContentVO.builder().user(AuthorVO.of(3)).build(),
                    ContentVO.builder().user(AuthorVO.of(4)).build()
            );
            User currentUser = User.convert(1);

            when(userService.selectByUserIdBatch(argThat(list ->
                    list.size() == 4 && list.containsAll(List.of(1, 2, 3, 4))
            ))).thenReturn(
                    List.of(
                            User.convert(1), User.convert(2), User.convert(3), User.convert(4)
                    )
            );
            when(subscriptionService.selectBatch(argThat(list ->
                    list.size() == 4 && list.containsAll(List.of(1, 2, 3, 4))
            ), eq(currentUser.getUserId()))).thenReturn(
                    List.of(
                            Subscription.builder().author(User.convert(1)).subscriptionId(1).build(),
                            Subscription.builder().author(User.convert(2)).subscriptionId(2).build(),
                            Subscription.builder().author(User.convert(3)).subscriptionId(3).build(),
                            Subscription.builder().author(User.convert(4)).subscriptionId(4).build()
                    )
            );

            var method = contentService.getClass().getDeclaredMethod("addContentUser", List.class, User.class);
            method.setAccessible(true);
            method.invoke(contentService, contentVOList, currentUser);


            assertEquals(true, contentVOList.get(0).getUser().getAttention());
            assertEquals(true, contentVOList.get(3).getUser().getAttention());
        }

        /**
         * @see ContentService#addContentCollection(List, User)
         */
        @Test
        public void 测试拉取内容时添加收藏数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var contentVOList = List.of(
                    ContentVO.builder().contentId(1).build(),
                    ContentVO.builder().contentId(2).build(),
                    ContentVO.builder().contentId(3).build(),
                    ContentVO.builder().contentId(4).build()
            );
            var currentUser = User.convert(1);

            when(collectionService
                    .selectCollectionCountBatchByType(eq(CollectionTypeEnum.CONTENT), argThat(list ->
                            list.size() == 4 && list.containsAll(List.of(1, 2, 3, 4))
                    ), eq(1)))
                    .thenReturn(
                            Map.of(
                                    1, CollectionCountDTO.builder().hasCollect(true).collectionCount(1L).build(),
                                    2, CollectionCountDTO.builder().hasCollect(true).collectionCount(2L).build(),
                                    3, CollectionCountDTO.builder().hasCollect(false).collectionCount(3L).build(),
                                    4, CollectionCountDTO.builder().hasCollect(false).collectionCount(4L).build()
                            )
                    );

            var method = contentService.getClass().getDeclaredMethod("addContentCollection", List.class, User.class);
            method.setAccessible(true);
            method.invoke(contentService, contentVOList, currentUser);

            assertEquals(true, contentVOList.get(1).getHasCollect());
            assertEquals(2L, (long) contentVOList.get(1).getCollectCount());
            assertEquals(false, contentVOList.get(3).getHasCollect());
            assertEquals(4L, (long) contentVOList.get(3).getCollectCount());

        }
    }

    /**
     * @see ContentService#publishContent(ContentDTO)
     */
    @Test
    public void 测试拉取内容() {
        Page page = Page.of(1, 5);
        var user = User.convert(1);
        when(userService.getCurrentUser()).thenReturn(user);
        Future f = mock(Future.class);
        when(executeService.submit(any(Runnable.class))).thenReturn(f);
        when(contentMapper.selectContentListPaging(page)).thenReturn(
                List.of(
                        Content.builder().contentId(1).user(user).build(),
                        Content.builder().contentId(2).user(user).build(),
                        Content.builder().contentId(3).user(user).build()
                )
        );

        var list = contentService.pullContents(page);

        assertEquals(3, list.size());
        assertEquals(2, (int) list.get(1).getContentId());
        verify(executeService, times(4)).submit(any(Runnable.class));
    }

    /**
     * @see ContentService#currentUserCollectContent(CollectionDTO) (List, User)
     */
    @Test
    public void 测试收藏内容应该失败() {
        CollectionDTO dto = new CollectionDTO();
        dto.setCollectionType(CollectionTypeEnum.CONTENT.getCode());
        dto.setContentId(1);

        when(userService.getCurrentUser()).thenReturn(User.convert(1));
        when(collectionService.selectByTypeAndContentId(eq(CollectionTypeEnum.CONTENT), eq(dto.getContentId()), eq(1))).thenReturn(new Collection());

        assertThrows(RuntimeException.class, () -> contentService.currentUserCollectContent(dto), R.COLLECT_EXIST);
    }

    /**
     * @see ContentService#currentUserCollectContent(CollectionDTO) (List, User)
     */
    @Test
    public void 测试收藏内容应该成功() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));
        when(collectionService.selectByTypeAndContentId(eq(CollectionTypeEnum.CONTENT), eq(1), eq(1))).thenReturn(null);
        when(collectionService.currentUserAddCollection(any())).thenReturn(1);

        CollectionDTO dto = new CollectionDTO();
        dto.setCollectionType(CollectionTypeEnum.CONTENT.getCode());
        dto.setContentId(1);

        contentService.currentUserCollectContent(dto);
    }

    /**
     * @see ContentService#createCurrentUserStateComment(ContentCommentDTO)
     */
    @Test
    public void 测试发布内容评论() {
        ContentCommentDTO dto = new ContentCommentDTO();
        dto.setContent("测试评论");
        dto.setToUser(1);
        dto.setContentId(1);
        var user = new User();
        user.setUserId(1);

        when(userService.getCurrentUser()).thenReturn(User.convert(1));
        when(userService.selectByPrimaryKey(eq(1))).thenReturn(User.convert(1));

        contentService.createCurrentUserStateComment(dto);

        verify(commentService).createNewCommentRecord(argThat(comment ->
                comment.getCommentType().equals(CommentTypeEnum.CONTENT.getCode())
                        && comment.getContent().equals(dto.getContent())
                        && comment.getFromUser().equals(user)
                        && comment.getTopicId().equals(dto.getContentId())
                        && comment.getToUser().equals(user)
        ));
    }

    /**
     * @see ContentService#selectContentCommentListPaging(Integer, Page)
     */
    @Test
    public void 测试拉取内容评论() {
        when(commentService.selectComments(eq(CommentTypeEnum.CONTENT), eq(1), any(Page.class))).thenReturn(
                List.of(
                        Comment.builder().commentId(1).fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                        Comment.builder().commentId(2).fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                        Comment.builder().commentId(3).fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                        Comment.builder().commentId(4).fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                        Comment.builder().commentId(5).fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                        Comment.builder().commentId(6).fromUser(User.convert(1)).toUser(User.convert(2)).build()
                )
        );
        when(userService.selectByUserIdBatch(argThat(list ->
                list.size() == 2 && list.containsAll(List.of(1, 2))
        ))).thenReturn(
                List.of(
                        User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                        User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build()
                )
        );

        var commentVOList = contentService.selectContentCommentListPaging(1, Page.of(1, 10));

        assertEquals(6, commentVOList.size());
        assertEquals(1, (int) commentVOList.get(0).getCommentId());
        assertEquals("用户1", commentVOList.get(0).getFromUser().getNickName());
        assertEquals("用户2", commentVOList.get(0).getToUser().getNickName());
    }

    /**
     * @see ContentService#selectByPrimaryKey(Integer)
     */
    @Test
    public void 测试根据ID查询单条内容() {
        when(contentMapper.selectByPrimaryKey(eq(1))).thenReturn(
                Content.builder()
                        .contentId(1)
                        .content("测试内容")
                        .title("测试标题")
                        .user(User.convert(1))
                        .build()
        );
        when(userService.selectByPrimaryKey(eq(1))).thenReturn(
                User.builder().userId(1)
                        .userInfo(UserInfo.builder().nickName("用户1").build()).build()
        );

        var contentVO = contentService.selectByPrimaryKey(1);

        assertEquals("测试内容", contentVO.getContent());
        assertEquals("测试标题", contentVO.getTitle());
        assertEquals("用户1", contentVO.getUser().getNickName());
    }

    /**
     * @see ContentService#selectTitleBatch(List)
     */
    @Test
    public void 测试批量获取内容标题() {
        when(contentMapper.selectBatch(argThat(list ->
                list.size() == 4 && list.containsAll(List.of(1, 2, 3, 4))
        ))).thenReturn(
                List.of(
                        Content.builder().contentId(1).title("内容1").build(),
                        Content.builder().contentId(2).title("内容2").build(),
                        Content.builder().contentId(3).title("内容3").build(),
                        Content.builder().contentId(4).title("内容4").build()
                )
        );

        var map = contentService.selectTitleBatch(List.of(1, 2, 3, 4));

        assertEquals("内容1", map.get(1));
        assertEquals("内容2", map.get(2));

    }


}