package com.fast.pay.app.notify.config;/**
 * Created by Administrator on 2018/3/16.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2018/3/16
 * Time: 9:28
 * Desc xxx
 */
@Configuration
@ConfigurationProperties(prefix = "notify")
@PropertySource("classpath:application.properties")
public class NotifyParamConfig {

    public static Map<Integer, Integer> notifyParam = new HashMap<Integer, Integer>();  // 通知时间次数map

    private String successValue;// 通知后用于判断是否成功的返回值。由HttpResponse获取

    public Map<Integer, Integer> getNotifyParam() {
        return notifyParam;
    }

    public void setNotifyParam(Map<Integer, Integer> notifyParam) {
        NotifyParamConfig.notifyParam = notifyParam;
    }

    public String getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(String successValue) {
        this.successValue = successValue;
    }

    public Integer getMaxNotifyTime() {
        return notifyParam == null ? 0 : notifyParam.size();
    }

}
