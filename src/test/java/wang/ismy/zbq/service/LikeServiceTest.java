package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.LikeMapper;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.content.ContentLikeDTO;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("点赞服务测试")
class LikeServiceTest {

    @Mock
    LikeMapper mapper;

    @Mock
    ExecuteService executeService;

    @Mock
    StateService stateService;

    @Mock
    InformService informService;

    @Mock
    ContentService contentService;
    @Mock
    TemplateEngineService templateEngineService;

    @Mock
    UserAccountService userAccountService;

    @Mock
    EmailService emailService;

    @InjectMocks
    LikeService likeService;

    /**
     * @see LikeService#createLikeRecord(LikeTypeEnum, Integer, User)
     */
    @Test
    public void 测试创建点赞记录() {

        likeService.createLikeRecord(LikeTypeEnum.CONTENT, 1, User.convert(1));

        verify(mapper).selectLikeByLikeTypeAndContentIdAndUserId(eq(1), eq(1), eq(1));
        verify(mapper).insertNew(argThat(like ->
                like.getContentId().equals(1)
                        && like.getLikeType().equals(1)
                        && like.getLikeUser().getUserId().equals(1)
        ));

        verify(executeService).submit(any(Runnable.class));
    }

    /**
     * @see LikeService#countLikeBatch(LikeTypeEnum, List)
     */
    @Test
    public void 批量获取内容点赞数() {
        var idList = List.of(1, 2, 3);

        when(mapper.countLikeByLikeTypeAndContentIdBatch(eq(1), argThat(l ->
                l == idList
        ))).thenReturn(
                List.of(
                        ContentLikeDTO.builder().id(1).count(1L).build(),
                        ContentLikeDTO.builder().id(2).count(2L).build(),
                        ContentLikeDTO.builder().id(3).count(3L).build()
                )
        );

        var map = likeService.countLikeBatch(LikeTypeEnum.CONTENT, idList);

        assertEquals(3, map.keySet().size());
        assertEquals(2L, (long) map.get(2));
    }

    /**
     * @see LikeService#countLike(Integer)
     */
    @Test
    public void 测试获取用户点赞数据() {
        when(mapper.countStateLikeByUserId(eq(1))).thenReturn(2L);

        when(mapper.countContentLikeByUserId(eq(1))).thenReturn(4L);

        var like = likeService.countLike(1);

        assertEquals(2L, (long) like.getStateLike());
        assertEquals(4L, (long) like.getContentLike());
        assertEquals(6L, (long) like.getTotal());
    }

    /**
     * @see LikeService#hasLikeBatch(LikeTypeEnum, List, Integer)
     */
    @Test
    public void 测试批量查询用户是否点赞() {

        var idList = List.of(1, 2, 3);

        when(mapper.selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(eq(1), argThat(l -> l == idList), eq(1)))
                .thenReturn(
                        List.of(
                                Map.of("content_id", 1, "has_like", 1L),
                                Map.of("content_id", 2, "has_like", 1L),
                                Map.of("content_id", 3, "has_like", 0L)
                        )
                );

        var map = likeService.hasLikeBatch(LikeTypeEnum.CONTENT, idList, 1);

        assertEquals(true, map.get(1));
        assertEquals(false, map.get(3));
    }

    @Nested
    class 点赞通知测试集 {

        /**
         * @see LikeService#informUserContentLike(Integer, User)
         */
        @Test
        public void 内容点赞通知() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, TemplateException, MessagingException {
            var likeUser = User.builder().userInfo(UserInfo.builder().nickName("点赞用户").build()).build();
            when(contentService.selectRaw(eq(1))).thenReturn(
                    Content.builder().user(User.builder().userId(1).build()).title("内容`").build()
            );

            when(templateEngineService.parseStr(eq(TemplateEngineService.LIKE_TEMPLATE), argThat(map ->
                    true
            ))).thenReturn("测试模板");

            when(userAccountService.selectAccountName(eq(UserAccountEnum.EMAIL), eq(1))).thenReturn("测试邮箱");
            when(templateEngineService.parseModel(eq("email/likeInform.html"), argThat(map ->
                    true
            ))).thenReturn("邮箱模板");
            var method = likeService.getClass().getDeclaredMethod("informUserContentLike", Integer.class, User.class);
            method.setAccessible(true);
            method.invoke(likeService, 1, likeUser);

            verify(informService).informUser(eq(1), eq("测试模板"));
            verify(emailService).sendHtmlMail(eq("测试邮箱"), eq("【转笔圈】有人给你的转笔内容点赞了"), eq("邮箱模板"));

        }

        /**
         * @see LikeService#infomUserStateLike(Integer, User)
         */
        @Test
        public void 动态点赞通知() throws IOException, TemplateException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, MessagingException {
            var likeUser = User.builder().userInfo(UserInfo.builder().nickName("点赞用户").build()).build();
            when(stateService.select(eq(1))).thenReturn(
                    State.builder().stateId(1).content("测试动态")
                            .user(User.convert(1))
                            .build()
            );

            when(templateEngineService.parseStr(eq(TemplateEngineService.LIKE_TEMPLATE), argThat(map ->
                    true
            ))).thenReturn("测试模板");

            when(userAccountService.selectAccountName(eq(UserAccountEnum.EMAIL), eq(1))).thenReturn("测试邮箱");
            when(templateEngineService.parseModel(eq("email/likeInform.html"), argThat(map ->
                    true
            ))).thenReturn("邮箱模板");
            var method = likeService.getClass().getDeclaredMethod("infomUserStateLike", Integer.class, User.class);
            method.setAccessible(true);
            method.invoke(likeService, 1, likeUser);

            verify(informService).informUser(eq(1), eq("测试模板"));
            verify(emailService).sendHtmlMail(eq("测试邮箱"), eq("【转笔圈】有人给你的笔圈动态点赞了"), eq("邮箱模板"));
        }

    }
}