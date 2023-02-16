package com.css.common.beans.response;

import com.css.common.beans.enums.ResultCode;

/**
 * Created by jiming.jing on 2020/3/23.
 */
public abstract class AbstractResult implements IResult {
    /**
     * 返回正常，状态 0
     */
    public static final int OK = ResultCode.OK.getCode();
    /**
     * 返回错误，状态999
     */
    public static final int ERROR = ResultCode.ERROR.getCode();

    /**
     * 返回码，0为正常，错误码参考
     * @see ResultCode
     */
    protected int code = OK;

    /* 描述 */
    protected String message = ResultCode.OK.getMessage();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
