package com.css.common.beans.enums;

/**
 * 操作类型枚举
 * Created by jiming.jing on 2019/12/18.
 */
public enum OperType {

    LOGIN(11),  //登录事件
    LOGOUT(11), //注销事件
    QUERY(5),   //普通业务查询事件
    ADD(21),    //普通业务新增操作
    DELETE(22), //普通业务删除操作
    UPDATE(23)  //普通业务更新操作
    ;

    private Integer value;

    OperType(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
}
