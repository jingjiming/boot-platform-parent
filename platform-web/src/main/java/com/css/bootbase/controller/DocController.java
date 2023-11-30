package com.css.bootbase.controller;

import com.css.common.beans.response.JsonResult;
import com.css.common.util.ReadDocUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by jiming.jing on 2023/2/1
 */
@RestController
@RequestMapping("/doc")
public class DocController {

    @GetMapping("/init")
    @ResponseBody
    public JsonResult init() {
        try {
            // 文档目录
            String path = "D:\\backup\\2";
            File dir = new File(path);
            File[] files = dir.listFiles();
            String resutl = null;
            // 计数
            for (File file : files) {
                InputStream is = new FileInputStream(file);
                resutl = ReadDocUtil.readDoc(is);
            }
            return JsonResult.ok(resutl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.badRequest();
    }
}
