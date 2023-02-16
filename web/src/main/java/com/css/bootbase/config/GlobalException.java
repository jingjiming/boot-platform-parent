package com.css.bootbase.config;

import com.css.common.beans.enums.ResultCode;
import com.css.common.beans.response.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult<?> handleException(Exception e) {
        logger.error("Exception[message:{}]", e.toString());
        e.printStackTrace();
        return JsonResult.badRequest(ResultCode.ERROR.getMessage());
    }
}
