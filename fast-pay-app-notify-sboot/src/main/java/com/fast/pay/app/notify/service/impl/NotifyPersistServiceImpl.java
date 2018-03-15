package com.fast.pay.app.notify.service.impl;

import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.app.notify.service.RpNotifyService;
import com.fast.pay.notify.entity.RpNotifyRecord;
import com.fast.pay.notify.entity.RpNotifyRecordLog;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 16:28
 * Desc xxx
 */
@Service
public class NotifyPersistServiceImpl implements NotifyPersistService {

    private RpNotifyService rpNotifyService;

    @Override
    public long saveNotifyRecord(RpNotifyRecord notifyRecord) {
        return rpNotifyService.createNotifyRecord(notifyRecord);
    }

    @Override
    public void updateNotifyRord(String id, int notifyTimes, String status) {
        RpNotifyRecord notifyRecord = rpNotifyService.getNotifyRecordById(id);
        notifyRecord.setNotifyTimes(notifyTimes);
        notifyRecord.setStatus(status);
        notifyRecord.setLastNotifyTime(new Date());
        rpNotifyService.updateNotifyRecord(notifyRecord);
    }

    @Override
    public long saveNotifyRecordLogs(String notifyId, String merchantNo, String merchantOrderNo, String request, String response, int httpStatus) {
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
