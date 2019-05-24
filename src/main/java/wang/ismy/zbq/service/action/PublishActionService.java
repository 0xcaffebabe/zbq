package wang.ismy.zbq.service.action;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.model.entity.course.Learning;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.ContentService;
import wang.ismy.zbq.service.StateService;
import wang.ismy.zbq.service.course.LearningService;
import wang.ismy.zbq.service.course.LessonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
public class PublishActionService {

    @Autowired
    private ContentService contentService;

    @Autowired
    private LearningService learningService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StateService stateService;

    @Autowired
    private Gson gson;

    public List<Action> pullActions(User user, Page page){

        // 拉取content
        var contentList = contentService.select(user.getUserId(),page);
        // 拉取lesson
        var learningList = learningService.select(user.getUserId(),page);
        var lessonList = lessonService.selectBatch(learningList.stream()
                .map(Learning::getLessonId).collect(Collectors.toList()));
        // 拉取state
        var stateList = stateService.select(user.getUserId(),page);

        // 全部转换为action
        List<Action> actionList = new ArrayList<>();

        contentList.forEach(e->{
            var action = ActionService.generateAction(ActionTypeEnum.PUBLISH,
                    user,
                    e.getContentId(),
                    "发表了转笔内容\""+e.getTitle()+"\"",
                    null,
                    e.getCreateTime());
            actionList.add(action);
        });

        lessonList.forEach(e->{
            var action = ActionService.generateAction(ActionTypeEnum.PUBLISH,
                    user,
                    e.getLessonId(),
                    "学完了课程\""+e.getLessonName()+"\"",
                    null,
                    e.getCreateTime());
            actionList.add(action);
        });

        stateList.forEach(e->{
            var action = ActionService.generateAction(ActionTypeEnum.PUBLISH,
                    user,
                    e.getStateId(),
                    "发表了一条动态",
                    e.getContent(),
                    e.getCreateTime());
            actionList.add(action);
        });

        return actionList;



    }
}
