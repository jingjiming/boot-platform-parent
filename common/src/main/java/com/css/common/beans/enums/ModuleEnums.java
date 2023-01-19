package com.css.common.beans.enums;

/**
 * 日志模块
 * Created by jiming.jing on 2019/12/18.
 */
public enum ModuleEnums {

    LOGIN("登录"),
    USER("用户管理");

    private String value;

    ModuleEnums(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
