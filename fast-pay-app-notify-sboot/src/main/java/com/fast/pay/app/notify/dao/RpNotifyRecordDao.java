package com.fast.pay.app.notify.dao;

import com.fast.pay.app.notify.entity.RpNotifyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */
@Mapper
public interface RpNotifyRecordDao {

    /**
     * 根据id主键查询
     * @param id
     * @return
     */
    RpNotifyRecord selectByPrimaryKey(String id);


    long deleteByPrimaryKey(String id);

    List<RpNotifyRecord> listBy(Map<String,Object> params);


    long insert(RpNotifyRecord record);


    long updateByPrimaryKey(RpNotifyRecord rpNotifyRecord);





}
