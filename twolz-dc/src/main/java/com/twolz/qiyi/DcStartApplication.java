package com.twolz.qiyi;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableDubboConfiguration
@MapperScan(basePackages = "com.twolz.qiyi.domain.mapper")
public class DcStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcStartApplication.class, args);
    }

}