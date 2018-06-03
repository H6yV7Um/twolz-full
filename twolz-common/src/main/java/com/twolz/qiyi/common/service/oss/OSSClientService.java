package com.twolz.qiyi.common.service.oss;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by liuwei
 * date 2017-05-16
 */

@Slf4j
@Service
public class OSSClientService {


    private @Value("system.common.alyunoss") String alyunoss;

    public void putOSSByByte(String key,byte[] content) throws Exception{
        OSSClientConfig ossClientConfig = OSSClientFactory.getInstance(alyunoss);
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        OSSClient ossClient = new OSSClient(ossClientConfig.getUrl(), ossClientConfig.getAppkey(), ossClientConfig.getSecret());
        ossClient.putObject(ossClientConfig.getBucketName(), key, new ByteArrayInputStream(content));
        // 关闭client
        ossClient.shutdown();
    }
    public void putOSSByFilePath(String key, String filePath) throws Exception{
        OSSClientConfig ossClientConfig = OSSClientFactory.getInstance(alyunoss);
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        OSSClient ossClient = new OSSClient(ossClientConfig.getUrl(), ossClientConfig.getAppkey(), ossClientConfig.getSecret());

        ossClient.putObject(ossClientConfig.getBucketName(), key, new File(filePath));
        // 关闭client
        ossClient.shutdown();
    }

    public void deleteObject(String key) throws Exception {
        OSSClientConfig ossClientConfig = OSSClientFactory.getInstance(alyunoss);
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME选项
        conf.setSupportCname(true);
        OSSClient ossClient = new OSSClient(ossClientConfig.getUrl(), ossClientConfig.getAppkey(), ossClientConfig.getSecret());
        ossClient.deleteObject(ossClientConfig.getBucketName(),key);
        // 关闭client
        ossClient.shutdown();
    }
}

interface OSSClientConfig {
    public String getAppkey();
    public String getSecret();
    public String getUrl();
    public String getBucketName();
}

class OSSClientFactory{
    public static final String ALIYUNOSS="ALIYUNOSS";
    public static final String ALIYUNOSS_TEST="ALIYUNOSS_TEST";
    private static  OSSClientConfig aliyunOssConfig;
    public static OSSClientConfig getInstance(String whichSmsProvider){
        assert StringUtils.isNoneEmpty(whichSmsProvider);
        if(whichSmsProvider.equals(ALIYUNOSS)){
            if (aliyunOssConfig == null) {
                aliyunOssConfig = new AliyunOssConfig();
            }
        } else if(whichSmsProvider.equals(ALIYUNOSS_TEST)){
            if (aliyunOssConfig == null) {
                aliyunOssConfig = new AliyunOssTestConfig();
            }
        }
        return aliyunOssConfig;
    }
    static final class AliyunOssConfig implements OSSClientConfig{
        @Getter
        private String Appkey = "LTAIPc6kF21K5EfO";
        @Getter
        private String secret = "oUzB2jB5u6JeRNhemRFsE05T2CqKMl";
        @Getter
        private String url = "oss-cn-hangzhou.aliyuncs.com";
        @Getter
        private String bucketName = "qidashi-file";
    }

    static final class AliyunOssTestConfig implements OSSClientConfig{

        @Getter
        private String appkey = "LTAIPc6kF21K5EfO";
        @Getter
        private String secret = "oUzB2jB5u6JeRNhemRFsE05T2CqKMl";
        @Getter
        private String url = "oss-cn-hangzhou.aliyuncs.com";
        @Getter
        private String bucketName = "dev-qidashi-file";
    }

}