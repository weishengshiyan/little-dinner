package com.ls.sell.utils;

import java.util.Random;

public class KeyUtil {

    public static synchronized String genUniqueKey() {
/**
 * 生成唯一的主键
 * 时间加随机数
 */

        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);

    }
}
