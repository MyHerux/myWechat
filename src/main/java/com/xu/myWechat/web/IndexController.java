package com.xu.myWechat.web;

import com.xu.myWechat.common.bean.ExceptionType;
import com.xu.myWechat.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xu
 * @version 1.0
 */
@RestController
public class IndexController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name="stringRedisTemplate")
    ValueOperations<String,String> valOpsStr;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        System.out.println("index");
        throw new BusinessException(ExceptionType.ADD_ERROR);
    }

    @RequestMapping("/t1")
    public String getSessionId() {
        valOpsStr.set("1234", "321");
        return valOpsStr.get("serviceName:ick_credit");
    }

}
