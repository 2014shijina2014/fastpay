package com.fast.pay.app.notify.mq.recv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fast.pay.app.notify.common.BizException;
import com.fast.pay.app.notify.config.MQConstant;
import com.fast.pay.app.notify.entity.RpNotifyRecord;
import com.fast.pay.app.notify.enums.NotifyStatusEnum;
import com.fast.pay.app.notify.service.MessageQueueService;
import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.app.notify.service.RpNotifyService;
import com.fast.pay.app.notify.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 15:45
 * Desc 交易异步通知监听消息队列
 */
@Component
@Slf4j
@RabbitListener(queues = MQConstant.QU_TRADE_NOTIFY)
public class TradeNotifyMessageListener {

    @Autowired
    private NotifyPersistService notifyPersistService;

    @Autowired
    private RpNotifyService rpNotifyService;

    @Autowired
    private MessageQueueService messageQueueService;

    /**
     *
     * @param ms
     */
    public void onMessage(String ms){
        try {
            log.info("== receive message:" + ms);
            JSON json = (JSON) JSONObject.parse(ms);
            RpNotifyRecord notifyRecord = JSONObject.toJavaObject(json, RpNotifyRecord.class);
            if (notifyRecord == null) {
                return;
            }
            notifyRecord.setStatus(NotifyStatusEnum.CREATED.name());
            notifyRecord.setCreateTime(new Date());
            notifyRecord.setLastNotifyTime(new Date());

            if ( !StringUtils.isEmpty(notifyRecord.getId())){
                RpNotifyRecord notifyRecordById = rpNotifyService.getNotifyRecordById(notifyRecord.getId());
                if (notifyRecordById != null){
                    return;
                }
            }

            try {
                // 将获取到的通知先保存到数据库中
                notifyPersistService.saveNotifyRecord(notifyRecord);
                notifyRecord = rpNotifyService.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getNotifyType());

                //直接将消息通知发送至死亡队列中，根据次数判断相应的延迟时间，并发送至死信队列等待路由  rabbitmq
                if(notifyRecord.getNotifyTimes().intValue() == 0){
                    messageQueueService.send(MQConstant.QU_ORDER_DLX_ROUTER, JsonUtils.objectToJson(notifyRecord),1);  //第一次立马发送，设置为1ms
                }
            }  catch (BizException e) {
                log.error("BizException :", e);
            } catch (Exception e) {
                log.error("exception{}",e);
            }
        }catch (Exception e){
            log.error("recv msg exception{}",e);
        } finally {
            //TODO
        }
    }

}
