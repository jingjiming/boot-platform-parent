package com.css.common.beans.enums;


import com.css.common.beans.error.IErrCode;

/**
 * Created by jiming.jing on 2020/2/26.
 */
public enum ResultCode implements IErrCode {

    OK(0, "success"),
    ERROR(999, "系统报错，请联系运维人员！"),
    SESSION_TIMEOUT(911, "session timeout."),
    DATA_ACCESS_ERROR(912, "数据访问异常"),
    ALPHA_ERROR_PARAMETER(40000, "参数错误"),
    ALPHA_ERROR_PARAMETER_NULL(40001, "空的请求参数"),
    ALPHA_ERROR_PARAMETER_INVALID(40002, "无效的请求参数"),

    NOT_LOGIN(-1, "用户登录信息失效，请退出重新登录"),
    NO_TOKEN(10001, "缺失token"),
    EXPIRED_TOKEN(10002, "token已过期")
    ;

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ResultCode toEnum(String name) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.toString().equalsIgnoreCase(name)) {
                return resultCode;
            }
        }
        return null;
    }
}
