package com.fast.pay.app.notify.service.impl;/**
 * Created by Administrator on 2018/3/15.
 */

import com.fast.pay.app.notify.dao.RpNotifyRecordDao;
import com.fast.pay.app.notify.service.RpNotifyService;
import com.fast.pay.notify.entity.RpNotifyRecord;
import org.springframework.stereotype.Service;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 15:53
 * Desc xxx
 */
@Service
public class RpNotifyServiceImpl implements RpNotifyService {

    private RpNotifyRecordDao rpNotifyRecordDao;

    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    @Override
    public RpNotifyRecord getNotifyRecordById(String id) {
        return rpNotifyRecordDao.selectByPrimaryKey(id);
    }

}
