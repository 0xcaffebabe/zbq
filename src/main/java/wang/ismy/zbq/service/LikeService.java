package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.LikeMapper;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.like.Like;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.LikeCountVO;
import wang.ismy.zbq.util.TimeUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author my
 */
@Service
@Slf4j
@Setter(onMethod_ = @Inject)
public class LikeService {

    private LikeMapper mapper;

    private ExecuteService executeService;

    private StateService stateService;

    private InformService informService;

    private ContentService contentService;

    private TemplateEngineService templateEngineService;

    private UserAccountService userAccountService;

    private EmailService emailService;

    public int createLikeRecord(LikeTypeEnum likeType, Integer contentId, User user) {

        if (mapper.selectLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(), contentId, user.getUserId()) != null) {
            ErrorUtils.error(R.LIKE_FAIL);
        }
        Like like = new Like();
        like.setLikeType(likeType.getCode());
        like.setContentId(contentId);
        like.setLikeUser(user);
        // 通知被点赞的用户
        informUser(likeType, contentId, user);

        return mapper.insertNew(like);
    }

    public int removeLikeRecord(LikeTypeEnum likeType, Integer contentId, User user) {
        return mapper.deleteLikeByLikeTypeAndContentIdAndUserId(likeType.getCode(), contentId, user.getUserId());
    }

    public List<Like> selectLikeListByLikeTypeAndContentId(LikeTypeEnum likeType, Integer contentId) {
        return mapper.selectLikeListByLikeTypeAndContentId(likeType.getCode(), contentId);
    }

    public List<Like> selectLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList) {
        if (contentIdList.size() == 0) {
            return List.of();
        }
        return mapper.selectLikeListByLikeTypeAndContentIdBatch(likeType.getCode(), contentIdList);
    }

    public Map<Integer, Long> countLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList) {
        if (contentIdList.size() == 0) {
            return Map.of();
        }
        var list = mapper.countLikeByLikeTypeAndContentIdBatch(likeType.getCode(), contentIdList);
        Map<Integer, Long> map = new HashMap<>();

        for (var i : list) {

            map.put(i.getId(), i.getCount());
        }
        return map;
    }

    public LikeCountVO countLike(Integer userId) {
        LikeCountVO vo = new LikeCountVO();
        long stateLike = mapper.countStateLikeByUserId(userId);
        long contentLike = mapper.countContentLikeByUserId(userId);
        vo.setStateLike(stateLike);
        vo.setContentLike(contentLike);
        vo.setTotal(stateLike + contentLike);
        return vo;
    }

    public Map<Integer, Boolean> hasLikeBatch(LikeTypeEnum likeType, List<Integer> contentIdList
            , Integer userId) {
        if (contentIdList.size() == 0) {
            return Map.of();
        }
        var list = mapper.selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(likeType.getCode(), contentIdList, userId);

        Map<Integer, Boolean> map = new HashMap<>();
        for (var i : list) {
            if (i.get("content_id") != null) {
                if ((Long) i.get("has_like") == 0) {
                    map.put((Integer) i.get("content_id"), false);
                } else {
                    map.put((Integer) i.get("content_id"), true);
                }

            }
        }
        return map;
    }

    public List<Like> select(Integer userId, Page page) {
        return mapper.selectLikeListByUserPaging(userId, page);
    }

    private void informUser(LikeTypeEnum likeType, Integer contentId, User user) {
        executeService.submit(() -> {

            switch (likeType) {
                case CONTENT:
                    // 通知内容发布者
                    informUserContentLike(contentId, user);
                    break;
                case STATE:
                    // 通知动态的发布者
                    infomUserStateLike(contentId,user);
                    break;
                default:
                    log.warn("未知点赞类型：{}", likeType);

            }

        });
    }

    private void informUserContentLike(Integer contentId, User likeUser) {
        Content content = contentService.selectRaw(contentId);

        if (content == null) {
            log.warn("content 不存在:{}", contentId);
            return;
        }

        var user = content.getUser();

        String title = content.getTitle().length() >= 15
                ? content.getTitle().substring(0, 15) + "..." : content.getTitle();

        informUserLike(likeUser,user,title,"转笔内容");

    }

    private void infomUserStateLike(Integer stateId, User likeUser){
        State state = stateService.select(stateId);

        if (state == null){
            log.warn("state 不存在:{}",stateId);
            return;
        }

        var user = state.getUser();
        String stateContent = state.getContent().length() >= 15
                ? state.getContent().substring(0, 15) + "..." : state.getContent();
        informUserLike(likeUser,user,stateContent,"笔圈动态");
    }

    private void informUserLike(User likeUser,User user,
                                String content,String type){

        // 点赞的人的昵称
        String nickName = likeUser.getUserInfo().getNickName();
        String time = TimeUtils.getStrTime();

        Map<String, Object> modelMap = Map.of("user", nickName,
                "content", content,
                "time", TimeUtils.getStrTime(),
                "type", type);
        informService.informUser(
                user.getUserId(),
                templateEngineService.parseStr(TemplateEngineService.LIKE_TEMPLATE,modelMap));

        // 邮箱通知

        String email = userAccountService.selectAccountName(UserAccountEnum.EMAIL, user.getUserId());

        if (email == null) {
            log.info("用户没有绑定邮箱:{},取消发送邮件", user.getUserId());
            return;
        }

        try {
            emailService.sendHtmlMail(email, "【转笔圈】有人给你的"+type+"点赞了",
                    templateEngineService.parseModel("email/likeInform.html",modelMap));
        } catch (MessagingException | IOException | TemplateException e) {
            log.warn("发送邮件发生异常:{}", e.getMessage());
        }

    }


}
