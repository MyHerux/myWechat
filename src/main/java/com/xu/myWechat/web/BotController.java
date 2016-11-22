package com.xu.myWechat.web;

import com.xu.myWechat.util.Aes;
import com.xu.myWechat.util.HttpHandler;
import com.xu.myWechat.util.Md5;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @author xu
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/bot")
public class BotController {

    @RequestMapping(value = "/doudou", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String bot(@RequestParam("str") String str) throws Exception {

        System.out.println(str);
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
        String result = HttpHandler.post("http://www.tuling123.com/openapi/api", json.toString());
        System.out.println(result);
        return result;
    }
}
