package wang.ismy.zbq.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;


@Service
public class OSSService {

    public String uploadImg(byte[] bytes, String format,String folder) {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAItmWponXWvipx";
        String accessKeySecret = "QAoqNFyNMN9F4M15JVUDSnYsxzXazN";
        String bucketName = "zbq88";


        String objectName =folder+"/"+ generateRandomImgName(format);

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        PutObjectResult result = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        ossClient.shutdown();

        return "http://zbq88.oss-cn-hangzhou.aliyuncs.com/"+objectName;

    }

    private String generateRandomImgName(String format){
        Random random = new Random(9999999);
        return String.valueOf(System.currentTimeMillis())+String.valueOf(random.nextInt())+"."+format;
    }
}
