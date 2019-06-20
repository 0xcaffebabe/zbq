package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.CollectionMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.CollectionCountDTO;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Collection;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("收藏服务测试")
class CollectionServiceTest {

    @Mock
    CollectionMapper mapper;

    @Mock
    UserService userService;

    @Mock
    ContentService contentService;

    @Mock
    ExecuteService executeService;

    @Mock
    InformService informService;

    @Mock
    EmailService emailService;

    @Mock
    TemplateEngineService templateEngineService;

    @Mock
    UserAccountService userAccountService;

    @InjectMocks
    CollectionService collectionService;

    /**
     * @see CollectionService#currentUserAddCollection(CollectionDTO)
     */
    @Test
    public void 测试当前用户添加收藏并且收藏时通知作者() {
        CollectionDTO dto = new CollectionDTO();
        dto.setContentId(1);
        dto.setCollectionType(CollectionTypeEnum.CONTENT.getCode());

        when(mapper.insertNew(argThat(c ->
                c.getContentId().equals(1)
                        && c.getCollectionType().equals(1)
        ))).thenReturn(1);


        collectionService.currentUserAddCollection(dto);

        verify(executeService).submit(any(Runnable.class));

    }

    /**
     * @see CollectionService#selectCollectionCountBatchByType(CollectionTypeEnum, List, Integer)
     */
    @Test
    public void 批量获取内容点赞数() {
        var idList = List.of(1, 2, 3);

        when(mapper.selectCollectionCountBatchByType(eq(1), argThat(l -> l == idList), eq(1)))
                .thenReturn(
                        List.of(
                                CollectionCountDTO.builder().contentId(1).collectionCount(1L).build(),
                                CollectionCountDTO.builder().contentId(2).collectionCount(2L).build(),
                                CollectionCountDTO.builder().contentId(3).collectionCount(3L).build()
                        )
                );
        var map = collectionService.selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT,idList,1);

        assertEquals(3,map.size());
        assertEquals(3L,(long)map.get(3).getCollectionCount());
    }

    /**
     * @see CollectionService#selectCurrentUserCollectionList(Page)
     */
    @Test
    public void 测试获取当前用户收藏列表() {

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(mapper.selectPaging(eq(1),any(Page.class))).thenReturn(
                List.of(
                        Collection.builder().contentId(1).collectionType(1).collectionId(1).build(),
                        Collection.builder().contentId(2).collectionType(1).collectionId(2).build(),
                        Collection.builder().contentId(3).collectionType(1).collectionId(3).build()
                )
        );

        when(mapper.selectCollectionCountBatchByType(eq(1), argThat(l -> l.containsAll(List.of(1,2,3))), eq(1)))
                .thenReturn(
                        List.of(
                                CollectionCountDTO.builder().contentId(1).collectionCount(1L).build(),
                                CollectionCountDTO.builder().contentId(2).collectionCount(2L).build(),
                                CollectionCountDTO.builder().contentId(3).collectionCount(3L).build()
                        )
                );

        when(contentService.selectTitleBatch(argThat(l->l.containsAll(List.of(1,2,3))))).thenReturn(
                Map.of(1,"内容1",2,"内容2",3,"内容3")
        );

        var collectionVOList = collectionService.selectCurrentUserCollectionList(Page.of(1,5));
        assertEquals(3,collectionVOList.size());
        assertEquals("内容1",collectionVOList.get(0).getSummary());
        assertEquals(3L,(long)collectionVOList.get(2).getCollectCount());

    }

    /**
     * @see CollectionService#informUserCollect(User, Integer, String, String)
     */
    @Test
    public void 测试收藏通知用户() throws IOException, TemplateException, MessagingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var currentUser = User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build();
        var author = 2;
        var type = "转笔内容";
        var content = "测试内容";

        when(userService.selectByPrimaryKey(eq(2))).thenReturn(User.convert(2));

        when(templateEngineService.parseStr(eq(TemplateEngineService.COLLECT_TEMPLATE),argThat(map->
                map.get("user").equals("用户1")
                && map.get("type").equals("转笔内容")
                && map.get("content").equals("测试内容")
                ))).thenReturn("测试模板");
        when(userAccountService.selectAccountName(eq(UserAccountEnum.EMAIL),eq(2)))
                .thenReturn("测试邮箱");
        when(templateEngineService.parseModel(eq("email/collectInform.html"),argThat(map->
                map.get("user").equals("用户1")
                        && map.get("type").equals("转笔内容")
                        && map.get("content").equals("测试内容")
        ))).thenReturn("测试模板");

        var method = collectionService.getClass().getDeclaredMethod("informUserCollect",User.class,Integer.class,String.class,String.class);
        method.setAccessible(true);
        method.invoke(collectionService,currentUser,author,type,content);

        verify(informService).informUser(eq(2),eq("测试模板"));
        verify(emailService).sendHtmlMail(eq("测试邮箱"),eq("【转笔圈】收藏通知"),eq("测试模板"));
    }

}