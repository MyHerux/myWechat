package com.xu.myWechat;

import com.xu.myWechat.pojo.dao.Visitor;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
public class MyWechatApplication {

    @Resource
    VisitorRepository visitorRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyWechatApplication.class, args);
	}

	@RequestMapping("")
	public String visit(HttpServletRequest request){

		Visitor visitor = new Visitor();
		visitor.setId(UUID.randomUUID().toString());
		visitor.setIp(request.getRemoteAddr());
		visitor.setVisitDate(new Date());


        visitorRepository.save(visitor);

		Long count =  visitorRepository.count();

		return String.format("你是来自%s的第%d位访问者。",request.getRemoteAddr(),count);
	}
}
