package com.twolz.qiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @author liuwei
 * API系统启动
 */
@SpringBootApplication
@MapperScan(basePackages = "com.twolz.qiyi.domain.mapper")
public class WebStartApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(WebStartApplication.class, args);
		
	}

}
