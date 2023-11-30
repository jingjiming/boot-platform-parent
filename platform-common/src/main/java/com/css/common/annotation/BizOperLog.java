package com.css.common.annotation;

import com.css.common.beans.enums.LogLevel;
import com.css.common.beans.enums.OperType;

import java.lang.annotation.*;

/**
 * 业务操作日志注解
 * Created by jiming.jing on 2021/7/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizOperLog {

    /**
     * 日志级别
     * @return
     */
    LogLevel level() default LogLevel.GENERAL;

    /**
     * 操作事件类型
     * @return
     */
    OperType type() default OperType.QUERY;

    /**
     * 备注
     * @return
     */
    String memo() default "";
}
