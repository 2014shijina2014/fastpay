package com.fast.pay.app.notify.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author chaun.lee
 * @Date 2018/3/15 22:20
 * @Company chaun
 * Created by Administrator on 2018/3/15.
 */
@Configuration
@Slf4j
public class QueueConfig {
    //信道配置
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(MQConstant.EX_ORDER_NOTIFY, true, false);
    }


    @Bean
    public Queue repeatTradeQueue() {
        Queue queue = new Queue(MQConstant.QU_ORDER_DLX_ROUTER,true,false,false);
        return queue;
    }

    @Bean
    public Binding  drepeatTradeBinding() {
        return BindingBuilder.bind(repeatTradeQueue()).to(defaultExchange()).with(MQConstant.QU_ORDER_DLX_ROUTER);
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", MQConstant.EX_ORDER_NOTIFY);
        arguments.put("x-dead-letter-routing-key", MQConstant.QU_ORDER_DLX_ROUTER);
        Queue queue = new Queue(MQConstant.QU_ORDER_DLX,true,false,false,arguments);
        log.info("arguments :" + queue.getArguments());
        return queue;
    }

    @Bean
    public Binding  deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(defaultExchange()).with(MQConstant.QU_ORDER_DLX);
    }

    /*********************    hello 队列  测试    *****************/
    @Bean
    public Queue queue() {
        Queue queue = new Queue(MQConstant.HELLO_QUEUE_NAME,true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(MQConstant.HELLO_QUEUE_NAME);
    }
}
