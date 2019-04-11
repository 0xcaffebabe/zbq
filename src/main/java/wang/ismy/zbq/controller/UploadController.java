package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.OSSService;
import wang.ismy.zbq.util.ErrorUtils;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private OSSService ossService;

    @PostMapping("/profile")
    @ResultTarget
    @MustLogin
    public Object uploadProfile(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();

        if (name == null){
            ErrorUtils.error(R.ERROR_IMG_NAME);
        }

        if (!(name.endsWith("png") || name.endsWith("jpg") || name.endsWith("gif"))){
            ErrorUtils.error(R.IMG_FORMAT_LIMIT);
        }

        String format = name.substring(name.lastIndexOf("."),name.length());

        return ossService.uploadImg(multipartFile.getBytes(),format,"img")+"?x-oss-process=style/square";
    }

    @PostMapping("/thumbnail")
    @ResultTarget
    @MustLogin
    public Object uploadThumbnail(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();

        if (name == null){
            ErrorUtils.error(R.ERROR_IMG_NAME);
        }

        if (!(name.endsWith("png") || name.endsWith("jpg") || name.endsWith("gif"))){
            ErrorUtils.error(R.IMG_FORMAT_LIMIT);
        }

        String format = name.substring(name.lastIndexOf("."),name.length());

        return ossService.uploadImg(multipartFile.getBytes(),format,"thumbnail");
    }

}
