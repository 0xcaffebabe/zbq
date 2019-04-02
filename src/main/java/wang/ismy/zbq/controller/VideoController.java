package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.service.VideoParseService;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoParseService videoParseService;

    @PutMapping("/analyze")
    @ResultTarget
    public Object analyzeVideoUrl(@RequestBody String url){
        return videoParseService.parse(url);
    }
}
