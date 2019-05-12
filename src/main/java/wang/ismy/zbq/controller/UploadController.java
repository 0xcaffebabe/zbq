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
import wang.ismy.zbq.service.system.ObjectStorageService;
import wang.ismy.zbq.util.ErrorUtils;

import java.io.IOException;

/**
 * @author my
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    private static final String[] IMG_FORMATS={"jpg","png","gif"};

    @Autowired
    private ObjectStorageService objectStorageService;

    @PostMapping("/profile")
    @ResultTarget
    @MustLogin
    public Object uploadProfile(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();

        if (name == null){
            ErrorUtils.error(R.ERROR_IMG_NAME);
        }

        String format = name.substring(name.lastIndexOf("."),name.length());

        if (!canProcess(format)){
            ErrorUtils.error(R.IMG_FORMAT_LIMIT);
        }

        return objectStorageService.uploadImg(multipartFile.getBytes(),format,"img")+"?x-oss-process=style/square";
    }

    @PostMapping("/thumbnail")
    @ResultTarget
    @MustLogin
    public Object uploadThumbnail(@RequestParam("file")MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();

        if (name == null){
            ErrorUtils.error(R.ERROR_IMG_NAME);
        }

        String format = name.substring(name.lastIndexOf("."),name.length());

        if (!canProcess(format)){
            ErrorUtils.error(R.IMG_FORMAT_LIMIT);
        }

        return objectStorageService.uploadImg(multipartFile.getBytes(),format,"thumbnail");
    }

    private boolean canProcess(String format){

        format = format.replaceAll("\\.","");

        for (var i : IMG_FORMATS){
            if (format.equalsIgnoreCase(i)){
                return true;
            }
        }
        return false;
    }

}
