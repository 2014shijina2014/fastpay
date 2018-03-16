package com.fast.pay.app.notify.config;

/**
 * @Author chaun.lee
 * @Date 2018/3/15 22:20
 * @Company chaun
 * Created by Administrator on 2018/3/15.
 * Rabbit消息队列相关常量
 */
public class MQConstant {

    private MQConstant(){

    }

    //exchange name
    public static final String EX_ORDER_NOTIFY = "ex_order_notify";

    //DLX QUEUE
    public static final String QU_ORDER_DLX = "qu.order.dlx";

    //DLX repeat QUEUE 死信转发队列
    public static final String QU_ORDER_DLX_ROUTER = "qu.order.dlx.router";

    //接收银行异步回调通知的队列
    public static final String QU_TRADE_NOTIFY = "qu.trade.notify";

    //Hello 测试消息队列名称
    //public static final String HELLO_QUEUE_NAME = "HELLO";



}
