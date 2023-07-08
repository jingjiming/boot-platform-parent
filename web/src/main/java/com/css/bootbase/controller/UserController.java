package com.css.bootbase.controller;

import com.css.bootbase.oss.core.OssTemplate;
import com.css.common.beans.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author jiming.jing
 * @since 2023/02/01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    OssTemplate ossTemplate;

    @GetMapping("/oss")
    public JsonResult oss() {
        this.ossTemplate.createBucket("bucket01");
        return JsonResult.ok();
    }

}
