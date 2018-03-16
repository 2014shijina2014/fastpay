package com.fast.pay.app.notify.service;

import com.fast.pay.app.notify.entity.RpNotifyRecord;
import com.fast.pay.app.notify.entity.RpNotifyRecordLog;

/**
 * Created by Administrator on 2018/3/16.
 */
public interface RpNotifyService {
    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    public RpNotifyRecord getNotifyRecordById(String id);


    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     *
     * @param merchantNo
     *            商户编号
     * @param merchantOrderNo
     *            商户订单号
     * @param notifyType
     *            消息类型
     * @return
     */
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo,
                                                                               String merchantOrderNo,
                                                                               String notifyType);

    /**
     * 插入记录
     * @param notifyRecord
     * @return
     */
    long createNotifyRecord(RpNotifyRecord notifyRecord);


    public void updateNotifyRecord(RpNotifyRecord rpNotifyRecord);


    long createNotifyRecordLog(RpNotifyRecordLog notifyRecordLog);










}
