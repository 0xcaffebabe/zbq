package wang.ismy.zbq.service;

import static org.junit.Assert.*;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.StateMapper;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.dto.state.StateCommentDTO;
import wang.ismy.zbq.model.dto.state.StateDTO;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.like.Like;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.model.vo.StateVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("动态服务测试")
public class StateServiceTest {

    @InjectMocks
    StateService stateService;

    @Mock
    UserService userService;

    @Mock
    StateMapper stateMapper;

    @Mock
    FriendService friendService;

    @Mock
    LikeService likeService;

    @Mock
    CommentService commentService;

    @Mock
    ExecuteService executeService;

    /**
     * @see StateService#publishState(StateDTO)
     */
    @Test
    public void 测试发布动态() {
        when(userService.getCurrentUser()).thenReturn(
                User.convert(1)
        );
        when(stateMapper.insertNew(argThat(s ->
                s.getUser().equals(User.convert(1))
                        && s.getContent().equals("测试内容")
        ))).thenReturn(1);

        StateDTO dto = new StateDTO();
        dto.setContent("测试内容");

        stateService.publishState(dto);

        verify(stateMapper).insertNew(argThat(s ->
                s.getContent().equals("测试内容") && s.getUser().getUserId().equals(1)
        ));

    }

    @Nested
    class 动态拉取测试集 {
        /**
         * @see StateService#pullState(Page)
         */
        @Test
        public void 测试拉取动态() {
            when(userService.getCurrentUser()).thenReturn(User.convert(1));
            when(friendService.selectFriendListByUserId(eq(1))).thenReturn(

                    new ArrayList<>(List.of(2, 3, 4, 5))
            );
            when(stateMapper.selectStateByUserIdBatchPaging(argThat(list ->
                    list.size() == 5 && list.containsAll(List.of(1, 2, 3, 4, 5))
            ), any(Page.class)))
                    .thenReturn(
                            List.of(
                                    State.builder().stateId(1).content("内容1").user(User.convert(1)).build(),
                                    State.builder().stateId(2).content("内容2").user(User.convert(2)).build(),
                                    State.builder().stateId(3).content("内容3").user(User.convert(3)).build(),
                                    State.builder().stateId(4).content("内容4").user(User.convert(4)).build(),
                                    State.builder().stateId(5).content("内容5").user(User.convert(5)).build()
                            )
                    );
            when(executeService.submit(any(Runnable.class))).thenReturn(mock(Future.class));

            when(userService.selectByUserIdBatch(argThat(list ->
                    list.size() == 5 && list.containsAll(List.of(1, 2, 3, 4, 5))
            ))).thenReturn(
                    List.of(
                            User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                            User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build(),
                            User.builder().userId(3).userInfo(UserInfo.builder().nickName("用户3").build()).build(),
                            User.builder().userId(4).userInfo(UserInfo.builder().nickName("用户4").build()).build(),
                            User.builder().userId(5).userInfo(UserInfo.builder().nickName("用户5").build()).build()
                    )
            );

            var stateVO = stateService.pullState(Page.of(1, 5));

            verify(executeService, times(2)).submit(any(Runnable.class));

            assertThat(stateVO)
                    .hasSize(5);
            assertEquals("用户1", stateVO.get(0).getUserVO().getNickName());
            assertEquals("内容1", stateVO.get(0).getContent());

            assertEquals("用户5", stateVO.get(4).getUserVO().getNickName());
            assertEquals("内容5", stateVO.get(4).getContent());
        }

        /**
         * @see StateService#addStateLikes(List, User)
         */
        @Test
        public void 测试拉取动态时添加点赞数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var stateVOList = List.of(
                    StateVO.builder().stateId(1).build(),
                    StateVO.builder().stateId(2).build(),
                    StateVO.builder().stateId(3).build(),
                    StateVO.builder().stateId(4).build(),
                    StateVO.builder().stateId(5).build()
            );
            when(likeService.selectLikeBatch(eq(LikeTypeEnum.STATE), argThat(list ->
                    list.size() == 5 && list.containsAll(List.of(1, 2, 3, 4, 5))
            ))).thenReturn(
                    List.of(
                            Like.builder().contentId(1).likeUser(User.convert(1)).build(),
                            Like.builder().contentId(1).likeUser(User.convert(2)).build(),
                            Like.builder().contentId(2).likeUser(User.convert(1)).build()
                    )
            );
            when(userService.selectByUserIdBatch(argThat(list ->
                    list.size() == 3 && list.containsAll(List.of(1, 2, 1))
            ))).thenReturn(
                    List.of(
                            User.convert(1),
                            User.convert(2)
                    )
            );

            var method = stateService.getClass().getDeclaredMethod("addStateLikes", List.class, User.class);
            method.setAccessible(true);
            method.invoke(stateService, stateVOList, User.convert(1));

            assertEquals(2, (int) stateVOList.get(0).getLikes().getLikeCount());
            assertEquals(1, (int) stateVOList.get(1).getLikes().getLikeCount());
            assertEquals(true, stateVOList.get(0).getLikes().getHasLike());
            assertEquals(true, stateVOList.get(1).getLikes().getHasLike());
            assertEquals(false, stateVOList.get(2).getLikes().getHasLike());
        }

        /**
         * @see StateService#addStateComment(List)
         */
        @Test
        public void 测试拉取动态时添加评论数据() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            var stateVOList = List.of(
                    StateVO.builder().stateId(1).build(),
                    StateVO.builder().stateId(2).build(),
                    StateVO.builder().stateId(3).build(),
                    StateVO.builder().stateId(4).build(),
                    StateVO.builder().stateId(5).build()
            );

            when(commentService.selectComments(eq(CommentTypeEnum.STATE), argThat(list ->
                    list.size() == 5 && list.containsAll(List.of(1, 2, 3, 4, 5))
            ))).thenReturn(
                    List.of(
                            Comment.builder().commentId(1).topicId(1).fromUser(User.convert(1)).build(),
                            Comment.builder().commentId(2).topicId(1).fromUser(User.convert(1)).build(),
                            Comment.builder().commentId(3).topicId(2).fromUser(User.convert(3)).build()
                    )
            );
            when(userService.selectByUserIdBatch(argThat(list ->
                    list.size() == 3 && list.containsAll(List.of(1, 1, 3))
            ))).thenReturn(
                    List.of(
                            User.convert(1), User.convert(3)
                    )
            );
            var method = stateService.getClass().getDeclaredMethod("addStateComment", List.class);
            method.setAccessible(true);
            method.invoke(stateService, stateVOList);

            assertEquals(2, stateVOList.get(0).getComments().size());
            assertEquals(1, (int) stateVOList.get(0).getComments().get(0).getFromUser().getUserId());
            assertEquals(1, stateVOList.get(1).getComments().size());
        }

    }

    /**
     * @see StateService#countSelfState()
     */
    @Test
    public void 测试计算本用户动态数() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));
        when(stateMapper.countStateByUserId(eq(1))).thenReturn(15);
        assertEquals(15, stateService.countSelfState());
    }

    /**
     * @see StateService#publishComment(StateCommentDTO)
     */
    @Test
    public void 测试发表动态评论() {

        StateCommentDTO dto = new StateCommentDTO();
        dto.setContent("测试评论");
        dto.setStateId(1);
        dto.setToUser(1);
        when(userService.selectByPrimaryKey(eq(1))).thenReturn(User.convert(1));
        when(userService.getCurrentUser()).thenReturn(User.convert(2));
        when(commentService.createNewCommentRecord(argThat(c ->
                c.getToUser().getUserId().equals(dto.getToUser())
                        && c.getTopicId().equals(dto.getStateId())
                        && c.getFromUser().getUserId().equals(2)
                        && c.getContent().equals(dto.getContent())
                        && c.getCommentType().equals(CommentTypeEnum.STATE.getCode())

        ))).thenReturn(1);

        assertEquals(1,stateService.publishComment(dto));
    }

    /**
     * @see StateService#deleteState(Integer)
     */
    @Nested
    class 动态删除测试集{
        @Test
        public void 测试删除动态应该成功() {
            when(stateMapper.selectByPrimaryKey(1)).thenReturn(State.builder().user(User.convert(1)).build());
            when(userService.getCurrentUser()).thenReturn(User.convert(1));
            when(stateMapper.setInvisibleByPrimaryKey(1)).thenReturn(1);

            assertEquals(1,stateService.deleteState(1));
        }

        @Test
        public void 测试删除动态但动态不存在() {
            when(userService.getCurrentUser()).thenReturn(User.convert(1));

            Assertions.assertThrows(RuntimeException.class,()->{
                stateService.deleteState(1);
            }, R.TARGET_STATE_NOT_EXIST);
        }

        @Test
        public void 测试删除动态但不属于当前用户() {
            when(stateMapper.selectByPrimaryKey(1)).thenReturn(State.builder().user(User.convert(2)).build());
            when(userService.getCurrentUser()).thenReturn(User.convert(1));

            Assertions.assertThrows(RuntimeException.class,()->{
                stateService.deleteState(1);
            },R.PERMISSION_DENIED);

        }
    }


}