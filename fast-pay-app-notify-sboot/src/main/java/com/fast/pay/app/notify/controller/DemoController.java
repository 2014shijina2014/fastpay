package com.fast.pay.app.notify.controller;

import com.alibaba.fastjson.JSON;
import com.fast.pay.app.notify.config.NotifyParamConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * User: lic
 * Date: 2018/3/15
 * Time: 15:29
 */
@Controller
@Slf4j
public class DemoController {
    @Autowired
    private NotifyParamConfig notifyParamConfig;

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        Map<Integer, Integer> params = notifyParamConfig.getNotifyParam();
        log.info(JSON.toJSONString(params)+";"+notifyParamConfig.getSuccessValue());
        return "hello";
    }

}
