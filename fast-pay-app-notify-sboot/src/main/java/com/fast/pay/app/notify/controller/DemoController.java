package com.fast.pay.app.notify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 15:29
 * Desc xxx
 */
@Controller
public class DemoController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

}
