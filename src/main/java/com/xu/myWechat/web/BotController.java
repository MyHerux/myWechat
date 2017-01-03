package com.xu.myWechat.web;

import com.xu.myWechat.VisitorRepository;
import com.xu.myWechat.common.bean.ExceptionType;
import com.xu.myWechat.common.util.Aes;
import com.xu.myWechat.common.util.HttpHandler;
import com.xu.myWechat.common.util.Md5;
import com.xu.myWechat.exception.BusinessException;
import com.xu.myWechat.pojo.dao.Visitor;
import net.sf.json.JSONObject;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author xu
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/bot")
public class BotController {

    @Resource
    VisitorRepository visitorRepository;

    private Logger logger = LogManager.getLogger();

    @RequestMapping(value = "/doudou", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String doudou(@RequestParam("str") String str) throws Exception {
        if (str == null) throw new BusinessException(ExceptionType.USER_ERROR);
        logger.info(str, str);
        //图灵网站上的secret
        String secret = "143859379038ac33";
        //图灵网站上的apiKey
        String apiKey = "19d51792b1b14f64b4485c78134006fb";
        //待加密的json数据
        JSONObject dataJson = new JSONObject();
        dataJson.put("key", apiKey);
        dataJson.put("info", str);
        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成密钥
        String keyParam = secret + timestamp + apiKey;
        String key = Md5.MD5(keyParam);

        //加密
        Aes mc = new Aes(key);
        String data = mc.encrypt(dataJson.toString());

        //封装请求参数
        JSONObject json = new JSONObject();
        json.put("key", apiKey);
        json.put("timestamp", timestamp);
        json.put("data", data);
        //请求图灵api
        JSONObject result = JSONObject.fromObject(HttpHandler.post("http://www.tuling123.com/openapi/api", json.toString()));
        logger.info(result);
        return result.getString("text");
    }

    @RequestMapping("/mongo")
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
