package com.css.common.beans.response;

import com.css.common.beans.enums.ResultCode;
import com.css.common.util.StringUtils;

import java.io.Serializable;

public class JsonResult<T> extends AbstractResult implements Serializable {

    /* 返回数据对象 */
    private T data;

    private JsonResult() {}

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = OK;
        rb.message = ResultCode.OK.getMessage();
        rb.data = data;
        return rb;
    }

    public static <T> JsonResult<T> okRequest() {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = OK;
        rb.message = ResultCode.OK.getMessage();
        rb.data = null;
        return rb;
    }

    public static <T> JsonResult<T> okRequest(String message) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = OK;
        rb.message = message;
        rb.data = null;
        return rb;
    }

    public static <T> JsonResult<T> okRequest(int code, String message) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = code;
        rb.message = message;
        rb.data = null;
        return rb;
    }

    public static <T> JsonResult<T> okRequest(int code, String message, T data) {
        JsonResult<T> rb = okRequest(code, message);
        rb.data = data;
        return rb;
    }

    /**
     * 构造错误返回对象
     * @param <T>
     * @return
     */
    public static <T> JsonResult<T> badRequest() {
        return JsonResult.badRequest(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage());
    }

    public static <T> JsonResult<T> badRequest(int code) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = code;
        rb.message = ResultCode.ERROR.getMessage();
        return rb;
    }

    public static <T> JsonResult<T> badRequest(String message) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = ERROR;
        rb.message = StringUtils.defaultIfEmpty(message, ResultCode.ERROR.getMessage());
        return rb;
    }

    public static <T> JsonResult<T> badRequest(int code, String message) {
        JsonResult<T> rb = new JsonResult<>();
        rb.code = code;
        rb.message = StringUtils.defaultIfEmpty(message, ResultCode.ERROR.getMessage());
        return rb;
    }

    public static <T> JsonResult<T> badRequest(String message, T data) {
        JsonResult<T> rb = badRequest(message);
        rb.data = data;
        return rb;
    }

    public static <T> JsonResult<T> badRequest(int code, String message, T data) {
        JsonResult<T> rb = badRequest(code, message);
        rb.data = data;
        return rb;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*--------------------- 链式编程，返回类本身 -------------------*/
    public JsonResult code(int code) {
        this.setCode(code);
        return this;
    }

    public JsonResult message(String message) {
        this.setMessage(message);
        return this;
    }

    public JsonResult data(T data) {
        this.setData(data);
        return this;
    }
}
