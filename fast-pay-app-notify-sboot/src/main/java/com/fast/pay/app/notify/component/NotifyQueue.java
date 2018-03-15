package com.fast.pay.app.notify.component;/**
 * Created by Administrator on 2018/3/15.
 */

import com.fast.pay.app.notify.service.NotifyPersistService;
import com.fast.pay.notify.entity.RpNotifyRecord;
import com.fast.pay.notify.enums.NotifyStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/15
 * Time: 16:54
 * Desc xxx
 */
@Component
@Slf4j
public class NotifyQueue {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private NotifyParam notifyParam;

    @Autowired
    private NotifyPersistService notifyPersistService;
    /**
     * 将传过来的对象进行通知次数判断，之后决定是否放在任务队列中
     *
     * @param notifyRecord
     * @throws Exception
     */
    public void addElementToList(RpNotifyRecord notifyRecord) {
        if (notifyRecord == null) {
            return;
        }
        Integer notifyTimes = notifyRecord.getNotifyTimes(); // 通知次数
        Integer maxNotifyTime = 0;
        try {
            maxNotifyTime = notifyParam.getMaxNotifyTime();
        } catch (Exception e) {
            log.error(e);
        }
        if (notifyRecord.getVersion().intValue() == 0) {// 刚刚接收到的数据
            notifyRecord.setLastNotifyTime(new Date());
        }
        long time = notifyRecord.getLastNotifyTime().getTime();
        Map<Integer, Integer> timeMap = notifyParam.getNotifyParams();
        if (notifyTimes < maxNotifyTime) {
            Integer nextKey = notifyTimes + 1;
            Integer next = timeMap.get(nextKey);
            if (next != null) {
                time += 1000 * 60 * next + 1;
                notifyRecord.setLastNotifyTime(new Date(time));
                App.tasks.put(new NotifyTask(notifyRecord, this, notifyParam));
            }
        } else {
            try {
                // 持久化到数据库中
                notifyPersist.updateNotifyRord(notifyRecord.getId(),
                        notifyRecord.getNotifyTimes(), NotifyStatusEnum.FAILED.name());
                log.info("Update NotifyRecord failed,merchantNo:" + notifyRecord.getMerchantNo() + ",merchantOrderNo:"
                        + notifyRecord.getMerchantOrderNo());
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
}
