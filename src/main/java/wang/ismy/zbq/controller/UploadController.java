package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.service.OSSService;

import java.awt.image.BufferedImage;
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
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getOriginalFilename());

        String name = multipartFile.getOriginalFilename();

        if (name == null){
            throw new RuntimeException("图片名称错误");
        }

        if (!(name.endsWith("png") || name.endsWith("jpg") || name.endsWith("gif"))){
            throw new RuntimeException("只能上传 jpg png gif 格式的图片！");
        }

        String format = name.substring(name.lastIndexOf("."),name.length());

        return ossService.uploadImg(multipartFile.getBytes(),format)+"?x-oss-process=style/square";
    }

}
