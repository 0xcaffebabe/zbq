package wang.ismy.zbq.service.system;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.model.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;


/**
 * @author my
 */
@Service
public class ObjectStorageService {

    public String uploadImg(byte[] bytes, String format, String folder) {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4Fp73MyJHygGhmM1cHJC";
        String accessKeySecret = "z7Pf3zld8OvA1ctSrEAKEE3nEIdv9F";
        String bucketName = "zbq88";


        String objectName = folder + "/" + generateRandomImgName(format);

        Credentials credentials = new Credentials() {
            @Override
            public String getAccessKeyId() {
                return "LTAItmWponXWvipx";
            }

            @Override
            public String getSecretAccessKey() {
                return "QAoqNFyNMN9F4M15JVUDSnYsxzXazN";
            }

            @Override
            public String getSecurityToken() {
                return null;
            }

            @Override
            public boolean useSecurityToken() {
                return false;
            }
        };
        OSSClient ossClient = new OSSClient(endpoint, new CredentialsProvider() {
            @Override
            public void setCredentials(Credentials creds) {

            }

            @Override
            public Credentials getCredentials() {
                return credentials;
            }
        }, new ClientConfiguration());


        PutObjectResult result = ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        ossClient.shutdown();

        return "//zbq88.oss-cn-hangzhou.aliyuncs.com/" + objectName;

    }

    private String generateRandomImgName(String format) {
        Random random = new Random(9999999);
        return String.valueOf(System.currentTimeMillis()) + String.valueOf(random.nextInt()) + "." + format;
    }
}
