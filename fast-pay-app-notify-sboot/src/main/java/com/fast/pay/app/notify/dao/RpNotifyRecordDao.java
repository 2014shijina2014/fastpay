package com.fast.pay.app.notify.dao;/**
 * Created by Administrator on 2018/3/15.
 */

import com.fast.pay.notify.entity.RpNotifyRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 16:20
 * Desc xxx
 */
@Mapper
public interface RpNotifyRecordDao {

    RpNotifyRecord selectByPrimaryKey(String id);

}
