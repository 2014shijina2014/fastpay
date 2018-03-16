package com.fast.pay.app.notify.service.impl;

import com.fast.pay.app.notify.config.DLXMessage;
import com.fast.pay.app.notify.config.MQConstant;
import com.fast.pay.app.notify.service.MessageQueueService;
import com.fast.pay.app.notify.utils.JsonUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date 2018/3/15 22:19
 * Created by Administrator on 2018/3/15.
 * 消息队列服务接口实现
 */
@Service
public class MessageQueueServiceImpl implements MessageQueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, String msg) {
        rabbitTemplate.convertAndSend(MQConstant.EX_ORDER_NOTIFY,queueName, msg);
    }

    @Override
    public void send(String queueName, String msg, final long times) {
        DLXMessage dlxMessage = new DLXMessage(queueName,msg,times);
        MessagePostProcessor processor = new MessagePostProcessor(){
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration(times + "");
                return message;
            }
        };
        dlxMessage.setExchange(MQConstant.EX_ORDER_NOTIFY);
        //消息发送至死亡队列中，经过times时间后会路由至相应的交换队列中
        rabbitTemplate.convertAndSend(MQConstant.EX_ORDER_NOTIFY,MQConstant.QU_ORDER_DLX, JsonUtils.objectToJson(dlxMessage), processor);
    }





}
