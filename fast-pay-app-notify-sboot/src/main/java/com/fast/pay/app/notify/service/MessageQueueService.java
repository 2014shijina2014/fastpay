package com.fast.pay.app.notify.service;

/**
 * @Author chaun.lee
 * @Date 2018/3/15 22:18
 * @Company chaun
 * Created by Administrator on 2018/3/15.
 * 消息队列服务接口
 */
public interface MessageQueueService {
    /**
     * 发送消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     */
    public void send(String queueName, String message);


    /**
     * 延迟发送消息到队列
     * @param queueName 队列名称
     * @param message 消息内容
     * @param times 延迟时间 单位毫秒
     */
    public void send(String queueName, String message, long times);

}
