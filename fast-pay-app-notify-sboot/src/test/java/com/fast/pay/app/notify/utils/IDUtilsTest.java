package com.fast.pay.app.notify.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by Administrator on 2018/3/16.
 */
@Slf4j
public class IDUtilsTest {
    @Test
    public void uuid() throws Exception {
      log.debug(IDUtils.uuid());
    }

}