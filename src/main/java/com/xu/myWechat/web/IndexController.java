package com.xu.myWechat.web;

import com.xu.myWechat.common.bean.ExceptionType;
import com.xu.myWechat.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu
 * @version 1.0
 */
@RestController
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        System.out.println("index");
        throw new BusinessException(ExceptionType.ADD_ERROR);
    }

}
