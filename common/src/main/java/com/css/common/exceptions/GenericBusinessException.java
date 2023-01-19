package com.css.common.exceptions;

import com.css.common.beans.error.IErrCode;

/**
 * Created by jiming.jing on 2020/3/25.
 */
public class GenericBusinessException extends RuntimeException {

    private int errorCode;

    public GenericBusinessException() {
    }

    public GenericBusinessException(String message) {
        super(message);
    }

    public GenericBusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public GenericBusinessException(IErrCode e) {
        super(e.getMessage());
        this.errorCode = e.getCode();
    }

    public GenericBusinessException(Throwable cause) {
        super(cause);
    }

    public GenericBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
