package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.service.video.parser.VideoParseService;
import wang.ismy.zbq.service.video.search.VideoSearchService;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoParseService videoParseService;

    @Autowired
    private VideoSearchService videoSearchService;
    @PutMapping("/analyze")
    @ResultTarget
    public Object analyzeVideoUrl(@RequestBody String url){
        return videoParseService.parse(url);
    }

    @GetMapping("/search")
    @ResultTarget
    @MustLogin
    @Limit(maxRequestPerMinute = 5)
    public Object searchVideo(@RequestParam("kw") String kw){
        return videoSearchService.search(kw);
    }
}
