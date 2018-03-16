package com.fast.pay.app.notify.dao;/**
 * Created by Administrator on 2018/3/16.
 */

import com.fast.pay.app.notify.entity.RpNotifyRecordLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Date: 2018/3/16
 * Time: 11:19
 * Desc xxx
 */
@Mapper
public interface RpNotifyRecordLogDao {

    long insert(RpNotifyRecordLog rpNotifyRecordLog);

    long deleteByPrimaryKey(String id);
}
