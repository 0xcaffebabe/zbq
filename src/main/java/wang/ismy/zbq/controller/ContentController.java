package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.ContentDTO;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.service.ContentService;

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

    @PutMapping("")
    @ResultTarget
    @MustLogin
    public Object createContent(@RequestBody @Valid ContentDTO contentDTO){
        contentService.currentUserPublish(contentDTO);
        return "发布成功";
    }
}
