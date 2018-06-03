package com.twolz.qiyi;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author liuwei
 * API系统启动
 */
@EnableSwagger2Doc
@SpringBootApplication
@EnableDubboConfiguration
@MapperScan(basePackages = "com.twolz.qiyi.domain.mapper")
public class ApiStartApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ApiStartApplication.class, args);
		
	}

}
