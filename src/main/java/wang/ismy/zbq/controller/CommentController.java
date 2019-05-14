package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.service.CommentService;

/**
 * @author my
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/list")
    @ResultTarget
    @MustLogin
    public Object selectComment(@RequestParam("type") Integer commentType,
                                @RequestParam("id") Integer contentId,
                                @RequestParam("page") Integer page,
                                @RequestParam("length") Integer length){
        return commentService.selectCommentVOList(CommentTypeEnum.of(commentType),
                contentId,Page.of(page,length));

    }
}
