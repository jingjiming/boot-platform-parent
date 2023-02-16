package com.css.common.exceptions;

import com.css.common.beans.enums.ResultCode;
import com.css.common.beans.response.IResult;
import com.css.common.beans.response.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 通用异常处理类
 * 异常采用统一的信息格式表示
 * Created by jiming.jing on 2020/4/2.
 */
@RestControllerAdvice
public class CommonExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(GenericBusinessException.class)
    public IResult handleException(GenericBusinessException e) {
        logger.error("GenericBusinessException[errorCode:{}, message:{}]", e.getErrorCode(), e.getMessage(), e);
        return JsonResult.badRequest(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public IResult handleException(DataAccessException e) {
        logger.error("DataAccessException[errorCode:{}, message:{}]", ResultCode.DATA_ACCESS_ERROR.getCode(), e.getMessage(), e);
        return JsonResult.badRequest(ResultCode.DATA_ACCESS_ERROR.getCode(), ResultCode.DATA_ACCESS_ERROR.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public IResult handleException(Throwable e) {
        logger.error("Throwable:[errorCode:{}, message:{}]", ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage());
        return JsonResult.badRequest(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public IResult handleException(Exception e) {
        logger.error("Exception[message:{}]", e.toString());
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.error(stringWriter.toString());
        return JsonResult.badRequest(ResultCode.ERROR.getMessage());
    }
}
