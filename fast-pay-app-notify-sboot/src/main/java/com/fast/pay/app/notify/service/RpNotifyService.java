package com.fast.pay.app.notify.service;/**
 * Created by Administrator on 2018/3/15.
 */

import com.fast.pay.notify.entity.RpNotifyRecord;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 15:53
 * Desc xxx
 */
public interface RpNotifyService {

    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    public RpNotifyRecord getNotifyRecordById(String id);




}
