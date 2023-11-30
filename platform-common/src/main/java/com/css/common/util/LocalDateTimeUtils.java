package com.css.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 * Java处理日期、日历和时间的方式一直为社区所诟病，将 java.util.Date设定为可变类型，以及SimpleDateFormat的非线程安全使其应用非常受限。
 * 新API基于ISO标准日历系统，java.time包下的所有类都是不可变类型而且线程安全。
 * </p>
 * Instant      时间戳
 * Duration     持续时间，时间差
 * LocalDate    只包含日期，比如：2020--1-15
 * LocalTime    只包含时间，比如：23:12:10
 * LocalDateTime  包含日期和时间，比如 2020-01-15 23:14:21
 * Period       时间段
 * ZoneOffset   时区偏移量，比如：+8:00
 * ZoneDateTime 带时区的时间
 * Clock        时钟，比如获取目前美国纽约的时间
 *
 * Created by jiming.jing on 2021/4/16.
 * @since jdk1.8+
 */
public class LocalDateTimeUtils {

    /**
     * 返回当前日期
     * @return
     */
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前时间
     * @return
     */
    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    /**
     * 返回特定日期
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static LocalDate of(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * 格式化日期时间
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     *  字符串转日期格式
     * @param str
     * @param pattern
     * @return
     */
    public static LocalDateTime parse(String str, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        // 字符串转日期
        LocalDateTime date = LocalDateTime.parse(str, format);
        return date;
    }

    /**
     * 几小时后的时间
     * @param hours
     * @return
     */
    public static LocalTime plusHours(int hours) {
        LocalTime time = LocalTime.now();
        return time.plusHours(hours);
    }

    /**
     * 几周后日期
     * @param week
     * @return
     */
    public static LocalDate plusWeek(int week) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        return nextWeek;
    }

    /**
     * 获取当前时间戳
     * 替代System.currentTimeInMillis()和TimeZone.getDefault()
     * @return
     */
    public static long currentTimeInMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 计算两个日期的间隔天数
     * @param date1
     * @param date2
     * @return
     */
    public static int betweenDays(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getDays();
    }


    public static void main(String[] args) {
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);

        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant " + timestamp.toEpochMilli());

        String dayAfterTommorrow = "20180205";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(dayAfterTommorrow+"  格式化后的日期为:  "+formatted);

    }
}
