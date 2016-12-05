
package com.xu.myWechat.common.web;

import com.xu.myWechat.common.bean.Response;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonHandlerExceptionResolver implements HandlerExceptionResolver {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsonHandlerExceptionResolver.class);


    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        ModelAndView mv = new ModelAndView();
        Map<String, String[]> parameterMap = request.getParameterMap();
        logException(handler, exception, parameterMap);
        JSONObject result = Response.json(exception);
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

    private void logException(Object handler, Exception exception, Map<String, String[]> parameterMap) {
        if (handler != null && HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Class<?> beanType = handlerMethod.getBeanType();
                String methodName = handlerMethod.getMethod().getName();
                RequestMapping controllerRequestMapping = beanType.getDeclaredAnnotation(RequestMapping.class);
                String classMapping = "";
                if (controllerRequestMapping != null) {
                    classMapping = controllerRequestMapping.value()[0];
                }
                RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                String methodMapping = "";
                if (methodRequestMapping != null) {
                    methodMapping = methodRequestMapping.value()[0];
                }
                if (!methodMapping.startsWith("/")) {
                    methodMapping = "/" + methodMapping;
                }
                Logger logger = LoggerFactory.getLogger(beanType);
                logger.error("RequestMapping is:");
                logger.error(classMapping + methodMapping);
                logger.error("HandlerMethod is:");
                logger.error(beanType.getSimpleName() + "." + methodName + "()");
                logger.error("ParameterMap is:");
                logger.error(parameterMap.toString(), exception);
            } catch (Exception e) {
                LOGGER.error(handler + " execute failed.", exception);
            }
        } else {
            LOGGER.error(handler + " execute failed.", exception);
        }
    }


}
