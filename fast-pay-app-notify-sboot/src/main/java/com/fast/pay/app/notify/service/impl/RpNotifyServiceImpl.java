package com.fast.pay.app.notify.service.impl;/**
 * Created by Administrator on 2018/3/16.
 */

import com.fast.pay.app.notify.dao.RpNotifyRecordDao;
import com.fast.pay.app.notify.entity.RpNotifyRecord;
import com.fast.pay.app.notify.entity.RpNotifyRecordLog;
import com.fast.pay.app.notify.service.RpNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2018/3/16
 * Time: 10:20
 * Desc xxx
 */
@Service
public class RpNotifyServiceImpl implements RpNotifyService {

    @Autowired
    private RpNotifyRecordDao rpNotifyRecordDao;

    @Override
    public RpNotifyRecord getNotifyRecordById(String id) {
        return rpNotifyRecordDao.selectByPrimaryKey(id);
    }

    @Override
    public RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        paramMap.put("notifyType",notifyType);
        List<RpNotifyRecord> records = rpNotifyRecordDao.listBy(paramMap);
        if(null != records && records.size() == 1){
            return records.get(0);
        }
        return null;
    }

    @Override
    public long createNotifyRecord(RpNotifyRecord notifyRecord) {
        return rpNotifyRecordDao.insert(notifyRecord);
    }

    @Override
    public void updateNotifyRecord(RpNotifyRecord rpNotifyRecord) {
        rpNotifyRecord.setEditTime(new Date());
        rpNotifyRecordDao.updateByPrimaryKey(rpNotifyRecord);
    }

    @Override
    public long createNotifyRecordLog(RpNotifyRecordLog notifyRecordLog) {
        return 0;
    }


}
