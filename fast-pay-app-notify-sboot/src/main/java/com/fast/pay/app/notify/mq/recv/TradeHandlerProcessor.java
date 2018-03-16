package com.fast.pay.app.notify.mq.recv;

import com.alibaba.fastjson.JSONObject;
import com.fast.pay.app.notify.common.BizException;
import com.fast.pay.app.notify.config.DLXMessage;
import com.fast.pay.app.notify.config.MQConstant;
import com.fast.pay.app.notify.entity.RpNotifyRecord;
import com.fast.pay.app.notify.enums.NotifyStatusEnum;
import com.fast.pay.app.notify.service.MessageQueueService;
import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.app.notify.utils.JsonUtils;
import com.fast.pay.app.notify.utils.http.SimpleHttpParam;
import com.fast.pay.app.notify.utils.http.SimpleHttpResult;
import com.fast.pay.app.notify.utils.http.SimpleHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Date 2018/3/15 22:33
 * Created by Administrator on 2018/3/15.
 * 处理交易通知信息并异步通知商户
 */
@Component
@Slf4j
@RabbitListener(queues = MQConstant.QU_ORDER_DLX_ROUTER)
public class TradeHandlerProcessor {

    @Autowired
    private MessageQueueService messageQueueService;

    @Autowired
    private NotifyPersistService notifyPersistService;

    @RabbitHandler
    public void process(String content) {
        DLXMessage message = JsonUtils.jsonToPojo(content, DLXMessage.class);
        if(null == message){
            //TODO
        }
        String contentJson = message.getContent();
       /* if(!StringUtils.isNotBlank(contentJson)){
            //TODO
        }*/

        RpNotifyRecord notifyRecord = JsonUtils.jsonToPojo(message.getContent(), RpNotifyRecord.class);
        try {
            // 得到当前通知对象的通知次数
            Integer notifyTimes = notifyRecord.getNotifyTimes();
            log.info("Notify Url " + notifyRecord.getUrl()+" ;notify id:"+notifyRecord.getId()+";notify times:"+notifyRecord.getNotifyTimes());

            /** 采用 httpClient */
            SimpleHttpParam param = new SimpleHttpParam(notifyRecord.getUrl());
            SimpleHttpResult result = SimpleHttpUtils.httpRequest(param);

            /*OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(notifyRecord.getUrl()).build();
            Response response = client.newCall(request).execute();
            Integer responseStatus = result.getStatusCode();*/

            notifyRecord.setNotifyTimes(notifyTimes + 1);
            //String successValue = notifyParam.getSuccessValue();
            String successValue = "";

            String responseMsg = "";

            Integer responseStatus = result.getStatusCode();
            // 得到返回状态，如果是200，也就是通知成功
            if (result != null
                    && (responseStatus == 200 || responseStatus == 201 || responseStatus == 202 || responseStatus == 203
                    || responseStatus == 204 || responseStatus == 205 || responseStatus == 206)) {
                responseMsg = result.getContent().trim();
                responseMsg = responseMsg.length() >= 600 ? responseMsg.substring(0, 600) : responseMsg;
                log.info("订单号： " + notifyRecord.getMerchantOrderNo() + " HTTP_STATUS：" + responseStatus + "请求返回信息：" + responseMsg);
                // 通知成功
                if (responseMsg.trim().equals(successValue)) {
                    notifyPersistService.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.SUCCESS.name());
                } else {
                    messageQueueService.send(MQConstant.QU_ORDER_DLX_ROUTER, JsonUtils.objectToJson(notifyRecord),notifyRecord.getNotifyTimes().intValue() * 1000);

                    notifyPersistService.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(),
                            NotifyStatusEnum.HTTP_REQUEST_SUCCESS.name());
                }
                log.info("Update NotifyRecord:" + JSONObject.toJSONString(notifyRecord)+";responseMsg:"+responseMsg);
            } else {
                messageQueueService.send(MQConstant.QU_ORDER_DLX_ROUTER, JsonUtils.objectToJson(notifyRecord),notifyRecord.getNotifyTimes().intValue() * 1000);
                // 再次放到通知列表中，由添加程序判断是否已经通知完毕或者通知失败
                notifyPersistService.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(),
                        NotifyStatusEnum.HTTP_REQUEST_FALIED.name());
            }

            // 写通知日志表
            notifyPersistService.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getUrl(), responseMsg, responseStatus);
            log.info("Insert NotifyRecordLog, merchantNo:" + notifyRecord.getMerchantNo() + ",merchantOrderNo:" + notifyRecord.getMerchantOrderNo());

        }catch (BizException e) {
            log.error("NotifyTask", e);
        }catch (Exception e) {
            log.error("NotifyTask", e);
            //根据次数并延长相应的时间1,2,5,10,20
            messageQueueService.send(MQConstant.QU_ORDER_DLX_ROUTER, JsonUtils.objectToJson(notifyRecord),notifyRecord.getNotifyTimes().intValue() * 1000);

            notifyPersistService.updateNotifyRord(notifyRecord.getId(), notifyRecord.getNotifyTimes(), NotifyStatusEnum.HTTP_REQUEST_FALIED.name());
            notifyPersistService.saveNotifyRecordLogs(notifyRecord.getId(), notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getUrl(), "", 0);
        }
        //发送至相应的路由队列 TODO
        //messageQueueService.send(message.getQueueName(), message.getContent());
    }
}
