package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.service.CollectionService;

/**
 * @author my
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/list")
    @MustLogin
    @ResultTarget
    public Object list(@RequestParam("page") Integer page,@RequestParam("length") Integer length){

        return collectionService.selectCurrentUserCollectionList(Page.of(page,length));
    }
}
