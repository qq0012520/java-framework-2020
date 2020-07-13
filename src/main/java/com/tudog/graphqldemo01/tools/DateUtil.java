package com.tudog.graphqldemo01.tools;

import java.time.format.DateTimeFormatter;

public class DateUtil {
    /**
     * 格式化日期为年月日（yyyyMMdd）
     */
    public static final DateTimeFormatter FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 格式化日期为时分秒（HHmmss）
     */
    public static final DateTimeFormatter FORMATTER_HHMISS = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * 格式化日期为时分秒毫秒（HH:mm:ss.SSS）
     */
    public static final DateTimeFormatter FORMATTER_HHMISSMILL = DateTimeFormatter.ofPattern("HHmmssSSS");

}