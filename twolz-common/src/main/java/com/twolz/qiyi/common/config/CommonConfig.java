package com.twolz.qiyi.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by liuwei
 * date 2017-03-30
 */
@Data
@Component
@ConfigurationProperties(prefix="system.common")
public class CommonConfig {

//    private static CommonConfig commonConfig = null;
//
//    private CommonConfig(){
//
//    }
//    private @Value("imgsDomain") String imgsDomain;
//
//    private @Value("alyunoss") String alyunoss;
//
//    private @Value("imgsLocalhostDomain") String imgsLocalhostDomain;
//
//    private @Value("imgRoot") String imgRoot;
//
//    private @Value("fileRoot") String fileRoot;
//
//    private @Value("${system.common.sms-duration}") Integer smsDuration;
//
//    private @Value("${system.common.sms-today-limit}") Integer smsTodayLimit;
//
//    private @Value("${system.common.sms-valid-time}") Integer smsValidTime;
//
//    @Bean
//    public static CommonConfig getInstance() throws Exception {
//        if(commonConfig == null) {
//            commonConfig = new CommonConfig();
//        }
//        return commonConfig;
//    }
}
