package com.fast.pay.app.notify.utils;/**
 * Created by Administrator on 2018/3/16.
 */


import java.util.UUID;

/**
 * Created by luban
 * User: lic
 * Date: 2018/3/16
 * Time: 10:16
 * Desc xxx
 */
public class IDUtils {

    private IDUtils(){

    }

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }




}
