package wang.ismy.zbq.service.action;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.CollectionService;
import wang.ismy.zbq.service.ContentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author my
 */
@Service
@Slf4j
public class CollectActionService {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private Gson gson;

    public List<Action> pullAction(User user, Page page) {

        var collectionList = collectionService.select(user.getUserId(), page);

        var contentIdList = new ArrayList<Integer>();

        collectionList.forEach(e -> {
            switch (CollectionTypeEnum.valueOf(e.getCollectionType())) {
                case CONTENT:
                    contentIdList.add(e.getContentId());
                    break;
                default:
                    log.info("未知收藏类型:{}", e);
            }
        });

        var contentList = contentService.selectBatch(contentIdList);

        List<Action> actionList = new ArrayList<>();
        contentList.forEach(e->{
            var action = ActionService.generateAction(ActionTypeEnum.COLLECTION,
                    user,
                    e.getContentId(),
                    "收藏了\""+e.getTitle()+"\"",
                    null,
                    e.getCreateTime());
            actionList.add(action);
        });
        return actionList;
    }
}
