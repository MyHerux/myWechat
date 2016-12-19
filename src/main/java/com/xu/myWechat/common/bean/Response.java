
package com.xu.myWechat.common.bean;


import com.xu.myWechat.exception.BusinessException;
import net.sf.json.JSONObject;

public class Response {

    public static JSONObject json(Exception exception) {
        JSONObject json = new JSONObject();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            json.put("code", businessException.getCode());
            json.put("message", businessException.getMessage());
            return json;
        } else {
            json.put("code", "500");
            json.put("message", "系统发生错误，请联系管理员");
            return json;
        }

    }

    public static JSONObject json(Object data) {
        JSONObject json = new JSONObject();
        json.put("code", "00");
        json.put("message", "succes");
        json.put("content", data);
        return json;
    }

}
