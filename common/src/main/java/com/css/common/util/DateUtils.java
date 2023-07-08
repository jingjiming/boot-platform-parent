package com.css.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: jiming.jing
 * @Date: 2017年12月1日
 * @Description:
 */
public class DateUtils {
    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 仅显示年月日，例如 2015-08-11.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 仅显示时分秒，例如 09:51:53.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * 显示年月日时分秒，例如 2015-08-11 09:51:53.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 每天的毫秒数 8640000.
     */
    public static final long MILLISECONDS_PER_DAY = 86400000L;
    /**
     * 每周的天数.
     */
    public static final long DAYS_PER_WEEK = 7L;
    /**
     * 每小时毫秒数.
     */
    public static final long MILLISECONDS_PER_HOUR = 3600000L;
    /**
     * 每分钟秒数.
     */
    public static final long SECONDS_PER_MINUTE = 60L;
    /**
     * 每小时秒数.
     */
    public static final long SECONDS_PER_HOUR = 3600L;
    /**
     * 每天秒数.
     */
    public static final long SECONDS_PER_DAY = 86400L;
    /**
     * 每个月秒数，默认每月30天.
     */
    public static final long SECONDS_PER_MONTH = 2592000L;
    /**
     * 每年秒数，默认每年365天.
     */
    public static final long SECONDS_PER_YEAR = 31536000L;

    /**
     * 常用的时间格式.
     */
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    /**
     * 得到当前日期字符串.
     * @return String 日期字符串，例如2015-08-11
     */
    public static String curDate() {
        return format(DateUtils.DATE_FORMAT);
    }

    /**
     * 得到当前时间字符串.
     * @return String 时间字符串，例如 09:51:53
     */
    public static String curTime() {
        return format(new Date(), DateUtils.TIME_FORMAT);
    }

    /**
     * 得到当前日期和时间字符串.
     * @return String 日期和时间字符串，例如 2015-08-11 09:51:53
     */
    public static String curDateTime() {
        return format(new Date(), DateUtils.DATETIME_FORMAT);
    }

    /**
     * 获取当前时间指定格式下的字符串.
     * @param pattern 转化后时间展示的格式，例如"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @return String 格式转换之后的时间字符串.
     */
    public static String format(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 获取指定日期的字符串格式.
     * @param date    需要格式化的时间，不能为空
     * @param pattern 时间格式，例如"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @return String 格式转换之后的时间字符串.
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 获取当前年份字符串.
     * @return String 当前年份字符串，例如 2015
     */
    public static String curYear() {
        return format(new Date(), "yyyy");
    }

    /**
     * 获取当前月份字符串.
     * @return String 当前月份字符串，例如 08
     */
    public static String curMonth() {
        return format(new Date(), "MM");
    }

    /**
     * 获取当前天数字符串.
     * @return String 当前天数字符串，例如 11
     */
    public static String curDay() {
        return format(new Date(), "dd");
    }

    /**
     * 获取当前星期字符串.
     * @return String 当前星期字符串，例如星期二
     */
    public static String curWeek() {
        return format(new Date(), "E");
    }

    /**
     * 将日期型字符串转换为日期格式.
     * 支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"
     *
     * @param str
     * @return Date
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将日期型字符串转换为日期格式.
     * 支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"
     *
     * @param str
     * @param pattern
     * @return Date
     */
    public static Date parseDate(Object str, String pattern) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), pattern);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前日期与指定日期相隔的天数.
     * @param date 给定的日期
     * @return long 日期间隔天数，正数表示给定日期在当前日期之前，负数表示在当前日期之后
     */
    public static long pastDays(Date date) {
        // 将指定日期转换为yyyy-MM-dd格式
        date = DateUtils.parseDate(DateUtils.format(date, DateUtils.DATE_FORMAT));
        // 当前日期转换为yyyy-MM-dd格式
        Date currentDate = DateUtils.parseDate(DateUtils.format(new Date(), DateUtils.DATE_FORMAT));
        long t = 0;
        if (date != null && currentDate != null) {
            t = (currentDate.getTime() - date.getTime()) / DateUtils.MILLISECONDS_PER_DAY;
        }
        return t;
    }

    /**
     * 获取当前日期指定天数之后的日期.
     * @param num 相隔天数
     * @return Date 日期
     */
    public static Date nextDay(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + num);
        return curr.getTime();
    }

    /**
     * 获取当前日期指定月数之后的日期.
     * @param num 间隔月数
     * @return Date 日期
     */
    public static Date nextMonth(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + num);
        return curr.getTime();
    }

    /**
     * 获取当前日期指定年数之后的日期.
     * @param num 间隔年数
     * @return Date 日期
     */
    public static Date nextYear(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + num);
        return curr.getTime();
    }

    /**
     * 将 Date 日期转化为 Calendar 类型日期.
     * @param date 给定的时间，若为null，则默认为当前时间
     * @return Calendar Calendar对象
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        // calendar.setFirstDayOfWeek(Calendar.SUNDAY);//每周从周日开始
        // calendar.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar;
    }

    /**
     * 计算两个日期之间相差天数.
     * @param start 计算开始日期
     * @param end   计算结束日期
     * @return long 相隔天数
     */
    public static long getDaysBetween(Date start, Date end) {
        // 将指定日期转换为yyyy-MM-dd格式
        start = DateUtils.parseDate(DateUtils.format(start, DateUtils.DATE_FORMAT));
        // 当前日期转换为yyyy-MM-dd格式
        end = DateUtils.parseDate(DateUtils.format(end, DateUtils.DATE_FORMAT));

        long diff = 0;
        if (start != null && end != null) {
            diff = (end.getTime() - start.getTime()) / DateUtils.MILLISECONDS_PER_DAY;
        }
        return diff;
    }

    /**
     * 计算两个日期之前相隔多少周.
     * @param start 计算开始时间
     * @param end   计算结束时间
     * @return long 相隔周数，向下取整
     */
    public static long getWeeksBetween(Date start, Date end) {
        return getDaysBetween(start, end) / DateUtils.DAYS_PER_WEEK;
    }

    /**
     * 获取与指定日期间隔给定天数的日期.
     * @param specifiedDay 给定的字符串格式日期，支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss",
     *                     "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
     *                     "yyyy/MM/dd HH:mm"
     * @param num          间隔天数
     * @return String 间隔指定天数之后的日期
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int num) {
        Date specifiedDate = parseDate(specifiedDay);
        Calendar c = Calendar.getInstance();
        c.setTime(specifiedDate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);
        String dayAfter = format(c.getTime(), DateUtils.DATE_FORMAT);
        return dayAfter;
    }

    /**
     * 计算两个日期之前间隔的小时数.
     * @param date1 结束时间
     * @param date2 开始时间
     * @return String 相差的小时数，保留一位小数
     */
    public static String dateMinus(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return "0";
        }
        Long r = date1.getTime() - date2.getTime();
        DecimalFormat df = new DecimalFormat("#.0");
        double result = r * 1.0 / DateUtils.MILLISECONDS_PER_HOUR;
        return df.format(result);
    }

    /**
     * 获取当前季度 .
     * @return Integer 当前季度数
     */
    public static Integer getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        Integer month = calendar.get(Calendar.MONTH) + 1;
        int season = 0;
        if (month >= 1 && month <= 3) {
            season = 1;
        } else if (month >= 4 && month <= 6) {
            season = 2;
        } else if (month >= 7 && month <= 9) {
            season = 3;
        } else if (month >= 10 && month <= 12) {
            season = 4;
        }
        return season;
    }

    /**
     * 将以秒为单位的时间转换为其他单位.
     * @param seconds 秒数
     * @return String 例如 16分钟前、2小时前、3天前、4月前、5年前等
     */
    public static String getIntervalBySeconds(long seconds) {
        StringBuffer buffer = new StringBuffer();
        if (seconds < SECONDS_PER_MINUTE) {
            buffer.append(seconds).append("秒前");
        } else if (seconds < SECONDS_PER_HOUR) {
            buffer.append(seconds / SECONDS_PER_MINUTE).append("分钟前");
        } else if (seconds < SECONDS_PER_DAY) {
            buffer.append(seconds / SECONDS_PER_HOUR).append("小时前");
        } else if (seconds < SECONDS_PER_MONTH) {
            buffer.append(seconds / SECONDS_PER_DAY).append("天前");
        } else if (seconds < SECONDS_PER_YEAR) {
            buffer.append(seconds / SECONDS_PER_MONTH).append("月前");
        } else {
            buffer.append(seconds / DateUtils.SECONDS_PER_YEAR).append("年前");
        }
        return buffer.toString();
    }

    /**
     * getNowTimeBefore(记录时间相当于目前多久之前)
     * @param seconds 秒
     * @return
     */
    public static String getNowTimeBefore(long seconds) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("上传于");
        if (seconds < 3600) {
            buffer.append((long) Math.floor(seconds / 60.0)).append("分钟前");
        } else if (seconds < 86400) {
            buffer.append((long) Math.floor(seconds / 3600.0)).append("小时前");
        } else if (seconds < 604800) {
            buffer.append((long) Math.floor(seconds / 86400.0)).append("天前");
        } else if (seconds < 2592000) {
            buffer.append((long) Math.floor(seconds / 604800.0)).append("周前");
        } else if (seconds < 31104000) {
            buffer.append((long) Math.floor(seconds / 2592000.0)).append("月前");
        } else {
            buffer.append((long) Math.floor(seconds / 31104000.0)).append("年前");
        }
        return buffer.toString();
    }

    /**
     * getMonthsBetween(查询两个日期相隔的月份)
     * @param startDate 开始日期1 (格式yyyy-MM-dd)
     * @param endDate   截止日期2 (格式yyyy-MM-dd)
     * @return
     */
    public static int getMonthsBetween(String startDate, String endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateUtils.parseDate(startDate));
        c2.setTime(DateUtils.parseDate(endDate));
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int month = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(year * 12 + month);
    }

    /**
     * getDayOfWeek(获取当前日期是星期几)
     * @param dateStr 日期
     * @return 星期几
     */
    public static String getDayOfWeek(String dateStr) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Date date = parseDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekOfDays[num];
    }

    /**
     * sns 格式 如几秒前，几分钟前，几小时前，几天前，几个月前，几年后， ... 精细，类如某个明星几秒钟之前发表了一篇微博
     * @param createTime
     * @return
     */
    public static String snsFormat(long createTime) {
        long now = System.currentTimeMillis() / 1000;
        long differ = now - createTime / 1000;
        String dateStr = "";
        if (differ <= 60) {
            dateStr = "刚刚";
        } else if (differ <= 3600) {
            dateStr = (differ / 60) + "分钟前";
        } else if (differ <= 3600 * 24) {
            dateStr = (differ / 3600) + "小时前";
        } else if (differ <= 3600 * 24 * 30) {
            dateStr = (differ / (3600 * 24)) + "天前";
        } else {
            Date date = new Date(createTime);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"
     * 如果获取失败，返回null
     * @return
     */
    public static String getUTCTimeStr() {
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
        UTCTimeBuffer.append(" ").append(hour).append(":").append(minute);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static Date getMonthLastDay() {
        return getMonthLastDay(getNow());
    }

    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1), calendar.get(2) + 1, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date getMonthFirstDay() {
        return getMonthFirstDay(getNow());
    }

    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date getYearLastDay() {
        return getYearLastDay(getNow());
    }

    public static Date getYearLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(1) + 1, 0, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date getYearFirstDay() {
        return getYearFirstDay(getNow());
    }

    public static Date getYearFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(6, 1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }

}
