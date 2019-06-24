package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CollectionMapper;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.CollectionCountDTO;
import wang.ismy.zbq.model.dto.CollectionDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Collection;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.vo.CollectionVO;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.TimeUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
@Setter(onMethod_ = @Inject)
public class CollectionService {

    private CollectionMapper mapper;

    private UserService userService;

    private ContentService contentService;

    private ExecuteService executeService;

    private InformService informService;

    private EmailService emailService;

    private TemplateEngineService templateEngineService;

    private UserAccountService userAccountService;

    public int currentUserAddCollection(CollectionDTO collectionDTO) {

        var currentUser = userService.getCurrentUser();

        Collection collection = new Collection();
        collection.setUser(currentUser);
        collection.setCollectionType(collectionDTO.getCollectionType());
        collection.setContentId(collectionDTO.getContentId());

        informUser(collectionDTO,currentUser);
        return mapper.insertNew(collection);
    }


    public Collection selectByTypeAndContentId(CollectionTypeEnum typeEnum, Integer contentId, Integer userId) {
        return mapper.selectByTypeAndContentIdAndUserId(typeEnum.getCode(), contentId, userId);
    }

    public Map<Integer, CollectionCountDTO> selectCollectionCountBatchByType(
            CollectionTypeEnum typeEnum,
            List<Integer> contentIdList,
            Integer userId) {

        if (contentIdList == null || contentIdList.size() == 0) {
            return Map.of();
        }

        var list = mapper.selectCollectionCountBatchByType(typeEnum.getCode(), contentIdList, userId);

        Map<Integer, CollectionCountDTO> ret = new HashMap<>();
        for (var i : list) {
            ret.put(i.getContentId(), i);
        }
        return ret;
    }

    public List<CollectionVO> selectCurrentUserCollectionList(Page page) {
        var currentUser = userService.getCurrentUser();

        var collectionList = mapper.selectPaging(currentUser.getUserId(), page);

        List<CollectionVO> collectionVOList = collectionList.stream()
                .map(CollectionVO::convert)
                .collect(Collectors.toList());

        var contentIdList = collectionVOList.stream()
                .map(CollectionVO::getContentId)
                .collect(Collectors.toList());

        var countMap = selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT, contentIdList, currentUser.getUserId());

        for (var i : collectionVOList) {

            var countVO = countMap.get(i.getContentId());

            if (countVO != null) {
                i.setCollectCount(countVO.getCollectionCount());
            }
        }

        addSummary(collectionVOList);

        return collectionVOList;

    }

    public List<Collection> select(Integer userId, Page page) {

        return mapper.selectPaging(userId, page);
    }

    private void informUser(CollectionDTO collectionDTO,User collectUser) {
        executeService.submit(() -> {

            switch (CollectionTypeEnum.valueOf(collectionDTO.getCollectionType())) {
                case CONTENT:
                    // TODO
                    Content content = contentService.selectRaw(collectionDTO.getContentId());
                    informUserCollect(collectUser,content.getUser().getUserId(),
                            "转笔内容",
                            content.getTitle());
                    break;
                case VIDEO:
                    // TODO
                    break;
                case COURSE:
                    // TODO
                    break;
                default:
                    log.error("未知收藏类型:"+collectionDTO.getCollectionType());
            }
        });
    }


    private void informUserCollect(User collectUser,Integer authorId,String type,String content){

        var author = userService.selectByPrimaryKey(authorId);
        if (author == null){
            log.error("收藏通知用户不存在:{}",authorId);
            return;
        }

        String nickName = collectUser.getUserInfo().getNickName();

        var map = Map.of("user",nickName,
                "time",TimeUtils.getStrTime(),
                "type",type,
                "content",content);
        // 系统通知用户
        String msg = templateEngineService.parseStr(TemplateEngineService.COLLECT_TEMPLATE,map);
        informService.informUser(authorId,msg);

        // 邮箱通知用户


        try {
            String html = templateEngineService.parseModel("email/collectInform.html",map);
            emailService.sendHtmlMail(authorId,"【转笔圈】收藏通知",html);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("发送邮件时发生异常：{}",e.getMessage());
        }


    }

    private void addSummary(List<CollectionVO> collectionVOList) {

        List<Integer> contentIdList = new ArrayList<>();

        for (var i : collectionVOList) {
            if (i.getCollectionType().equals(CollectionTypeEnum.CONTENT.getCode())) {
                contentIdList.add(i.getContentId());
            }
        }

        var map = contentService.selectTitleBatch(contentIdList);

        for (var i : collectionVOList) {
            if (i.getCollectionType().equals(CollectionTypeEnum.CONTENT.getCode())) {
                String title = map.get(i.getContentId());
                i.setSummary(title);
            }
        }
    }


}
