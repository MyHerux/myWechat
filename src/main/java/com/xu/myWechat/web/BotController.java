package com.xu.myWechat.web;

import com.xu.myWechat.common.bean.ExceptionType;
import com.xu.myWechat.common.util.Aes;
import com.xu.myWechat.common.util.HttpHandler;
import com.xu.myWechat.common.util.Md5;
import com.xu.myWechat.exception.BusinessException;
import com.xu.myWechat.mapper.BotXMapper;
import com.xu.myWechat.pojo.dao.BotX;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/bot")
public class BotController {

    private Logger logger = LogManager.getLogger();

    private final BotXMapper botXMapper;

    @Autowired
    public BotController(BotXMapper botXMapper) {
        this.botXMapper = botXMapper;
    }

    @ApiOperation(value = "获取用户信息",response = String.class, notes = "根据用户名获取用户服务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户的姓名"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "用户的密码"),
            @ApiImplicitParam(paramType = "query", name = "str", dataType = "String", required = true, value = "用户数据")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
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
        String result = HttpHandler.post("http://www.tuling123.com/openapi/api", json.toString());
        logger.info(result);
        return result;
    }

    @RequestMapping(value = "/xuhua", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String xuhua() throws Exception {
        BotX botX = botXMapper.findById(1);
        logger.info(botX.getName());
        return botX.getName();
    }
}
