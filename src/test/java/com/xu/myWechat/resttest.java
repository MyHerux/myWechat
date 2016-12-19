package com.xu.myWechat;

import com.xu.myWechat.common.util.HttpHandler;
import org.junit.Test;

/**
 * Created by xu on 2016/12/6.
 */
public class resttest {

    @Test
    public void t1() throws Exception {
        System.out.println(HttpHandler.get("http://localhost:8080"));
    }
}
