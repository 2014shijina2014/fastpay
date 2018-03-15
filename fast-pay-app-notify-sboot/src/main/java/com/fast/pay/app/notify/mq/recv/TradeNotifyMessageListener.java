package com.fast.pay.app.notify.mq.recv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.app.notify.service.RpNotifyService;
import com.fast.pay.common.core.exception.BizException;
import com.fast.pay.common.core.utils.StringUtil;
import com.fast.pay.notify.entity.RpNotifyRecord;
import com.fast.pay.notify.enums.NotifyStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 15:45
 * Desc 交易通知监听消息
 */
@Component
@Slf4j
public class TradeNotifyMessageListener {

    @Autowired
    private NotifyPersistService notifyPersistService;

    @Autowired
    private RpNotifyService rpNotifyService;


    @JmsListener(destination = "tradeNotify")
    public void recv(String ms){
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

            if ( !StringUtil.isEmpty(notifyRecord.getId())){
                RpNotifyRecord notifyRecordById = rpNotifyService.getNotifyRecordById(notifyRecord.getId());
                if (notifyRecordById != null){
                    return;
                }
            }

            while (rpNotifyService == null) {
                Thread.currentThread().sleep(1000); // 主动休眠，防止类Spring 未加载完成，监听服务就开启监听出现空指针异常
            }
            try {
                // 将获取到的通知先保存到数据库中
                notifyPersistService.saveNotifyRecord(notifyRecord);
                notifyRecord = rpNotifyService.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getNotifyType());

                // 添加到通知队列
                notifyQueue.addElementToList(notifyRecord);
            }  catch (BizException e) {
                log.error("BizException :", e);
            } catch (Exception e) {
                log.error(e);
            }
        }catch (Exception e){
            log.error("recv msg exception{}",e);
        } finally {

        }
    }

}
