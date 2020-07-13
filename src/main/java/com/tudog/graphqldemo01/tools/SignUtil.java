package com.tudog.graphqldemo01.tools;

import java.time.LocalTime;

/**
 * 标识工具类，用于生成各种标识标记，比如随机文件名或者实体ID
 */
public class SignUtil {
    private SignUtil(){
        throw new RuntimeException("Not support initiating the Class");
    }

    /**
     * 根据时间规则生成文件名
     * 文件名规则为：用户名 + 下划线 + 时 + 分 + 秒 + 毫秒
     * @return
     */
    public static String fileNameByTime(){
        return HttpUserTool.getName() + "_" + LocalTime.now().format(DateUtil.FORMATTER_HHMISSMILL);
    }
}