package com.xu.myWechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@SpringBootApplication
@EnableSwagger2
public class MyWechatApplication {

    @Resource
    VisitorRepository visitorRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyWechatApplication.class, args);
	}
}
