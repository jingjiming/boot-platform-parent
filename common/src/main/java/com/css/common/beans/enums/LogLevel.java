package com.css.common.beans.enums;

/**
 * 日志级别枚举
 * Created by jiming.jing on 2021/7/28.
 */
public enum LogLevel {

    GENERAL(1),   //一般
    IMPORTANT(2), //重要
    SERIOUS(3)    //严重
    ;
    private Integer value;

    LogLevel(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
}
