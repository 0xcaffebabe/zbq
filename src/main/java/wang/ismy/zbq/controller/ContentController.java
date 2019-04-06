package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.ContentCommentDTO;
import wang.ismy.zbq.dto.ContentDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.ContentService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/list")
    @ResultTarget
    @MustLogin
    public Object list(@RequestParam("page") Integer page,@RequestParam("length") Integer length){
        Page p = new Page(page,length);
        return contentService.selectContentListPaging(p);
    }

    @GetMapping("/{id}")
    @ResultTarget
    @MustLogin
    public Object getById(@PathVariable("id") Integer contentId){
        return contentService.selectByPrimaryKey(contentId);
    }

    @PutMapping("")
    @ResultTarget
    @MustLogin
    public Object createContent(@RequestBody @Valid ContentDTO contentDTO){
        contentService.currentUserPublish(contentDTO);
        return "发布成功";
    }

    @PutMapping("/comment")
    @ResultTarget
    @MustLogin
    public Object comment(@RequestBody @Valid ContentCommentDTO dto){
        if (contentService.createCurrentUserStateComment(dto) != 1){
            ErrorUtils.error(StringResources.COMMENT_FAIL);
        }

        return StringResources.COMMENT_SUCCESS;
    }

    @GetMapping("/comment/{contentId}")
    @ResultTarget
    @MustLogin
    public Object commentList(@PathVariable("contentId") Integer contentId,@RequestParam("page") Integer page,@RequestParam("length") Integer length){
        Page p = new Page(page,length);
        return contentService.selectContentCommentListPaging(contentId,p);
    }
}
