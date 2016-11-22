package com.xu.myWechat.web;

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
        return "hello world!";
    }

}
