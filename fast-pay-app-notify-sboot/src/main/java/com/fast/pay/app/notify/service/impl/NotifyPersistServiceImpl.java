package com.fast.pay.app.notify.service.impl;/**
 * Created by Administrator on 2018/3/16.
 */

import com.fast.pay.app.notify.entity.RpNotifyRecord;
import com.fast.pay.app.notify.entity.RpNotifyRecordLog;
import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.app.notify.service.RpNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/16
 * Time: 10:37
 * Desc xxx
 */
@Service
public class NotifyPersistServiceImpl implements NotifyPersistService {
    @Autowired
    private RpNotifyService rpNotifyService;

    /**
     * 创建商户通知记录.<br/>
     *
     * @param notifyRecord
     * @return
     */
    @Override
    public long saveNotifyRecord(RpNotifyRecord notifyRecord) {
        return rpNotifyService.createNotifyRecord(notifyRecord);
    }

    /**
     * 更新商户通知记录.<br/>
     *
     * @param id
     * @param notifyTimes
     *            通知次数.<br/>
     * @param status
     *            通知状态.<br/>
     * @return 更新结果
     */
    @Override
    public  void updateNotifyRord(String id, int notifyTimes, String status) {
        RpNotifyRecord notifyRecord = rpNotifyService.getNotifyRecordById(id);
        notifyRecord.setNotifyTimes(notifyTimes);
        notifyRecord.setStatus(status);
        notifyRecord.setLastNotifyTime(new Date());
        rpNotifyService.updateNotifyRecord(notifyRecord);
    }

    /**
     * 创建商户通知日志记录.<br/>
     *
     * @param notifyId
     *            通知记录ID.<br/>
     * @param merchantNo
     *            商户编号.<br/>
     * @param merchantOrderNo
     *            商户订单号.<br/>
     * @param request
     *            请求信息.<br/>
     * @param response
     *            返回信息.<br/>
     * @param httpStatus
     *            通知状态(HTTP状态).<br/>
     * @return 创建结果
     */
    @Override
    public long saveNotifyRecordLogs(String notifyId, String merchantNo, String merchantOrderNo, String request, String response,
                                     int httpStatus) {
        RpNotifyRecordLog notifyRecordLog = new RpNotifyRecordLog();
        notifyRecordLog.setHttpStatus(httpStatus);
        notifyRecordLog.setMerchantNo(merchantNo);
        notifyRecordLog.setMerchantOrderNo(merchantOrderNo);
        notifyRecordLog.setNotifyId(notifyId);
        notifyRecordLog.setRequest(request);
        notifyRecordLog.setResponse(response);
        notifyRecordLog.setCreateTime(new Date());
        notifyRecordLog.setEditTime(new Date());
        return rpNotifyService.createNotifyRecordLog(notifyRecordLog);
    }

}
