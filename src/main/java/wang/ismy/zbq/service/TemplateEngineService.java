package wang.ismy.zbq.service;


import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import wang.ismy.zbq.util.TimeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author my
 */
@Service
public class TemplateEngineService {

    public static final String COMMENT_TEMPLATE =
            "用户【@@{user}】评论了你的@@{type}【@@{content}】:‘@@{comment}’[@@{time}]";

    public static final String LIKE_TEMPLATE =
            "用户【@@{user}】给你的@@{type}【@@{content}】点了赞[@@{time}]";

    public static final String COLLECT_TEMPLATE =
            "用户【@@{user}】收藏了你的@@{type}【@@{content}】[@@{time}]";


    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public String parseStr(String template,Map<String, ?> map) {

        for (var key : map.keySet()){

            template = template.replace("@@{"+key+"}",map.get(key).toString());

        }

        template = template.replace("@@{time}",TimeUtils.getStrTime());
        return template;
    }


    public String parseModel(String modelName,Map<String, ?> map) throws IOException, TemplateException {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(modelName);

        Map<String,Object> modelMap = new HashMap<>();

        for (var k : map.keySet()){
            modelMap.put(k,map.get(k));
        }

        modelMap.put("time",TimeUtils.getStrTime());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template,modelMap);
    }
}
