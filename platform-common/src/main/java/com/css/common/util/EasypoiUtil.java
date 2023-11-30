package com.css.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 导出、导入Excel文件工具类
 * Created by jiming.jing on 2021/1/8.
 */
public class EasypoiUtil {

    public static void exportExcel(String fileName, Workbook workbook, HttpServletResponse response) {
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名。创建表头
     *
     * @param list           导出的实体类
     * @param title          表头名称
     * @param sheetName      sheet表名
     * @param pojoClass      映射的实体类
     * @param isCreateHeader 是否创建表头
     * @param fileName       文件名
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {

        ExportParams exportParams = new ExportParams(title, sheetName);

        exportParams.setCreateHeadRows(isCreateHeader);

        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
     *
     * @param list      导出的实体类
     * @param title     表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param fileName  文件名
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {

        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));

    }

    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response) {

        defaultExport(list, pojoClass, fileName, response);

    }

    /**
     * 功能描述：Map 集合导出
     *
     * @param list     实体集合
     * @param fileName 导出的文件名称
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }


    /**
     * 功能描述：默认导出方法
     *
     * @param list         导出的实体集合
     * @param fileName     导出的文件名
     * @param pojoClass    pojo实体
     * @param exportParams ExportParams封装实体
     * @param response
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);

        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }


    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response) {

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), pojoClass, list);

        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }
    /**
     * 功能描述：Excel导出
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook Excel对象
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 2007版本Excel
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 功能描述：默认导出方法
     *
     * @param list     导出的实体集合
     * @param fileName 导出的文件名
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {

        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);

        if (workbook != null) ;

        downLoadExcel(fileName, response, workbook);

    }


    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @param filePath   文件路径
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 功能描述：根据工作流来导入Excel
     *
     * @param inputStream   文件路径
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(InputStream inputStream, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (inputStream==null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(inputStream, pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空或模板选择错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @param file       上传的文件
     * @param titleRows  表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass  Excel实体类
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {

        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        //params.setLastOfInvalidRow(1);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * 下拉选项数据所在sheet页
     * @param workbook
     * @param sheetName
     * @param firstCol 区域中第一个单元格的列号（下标0开始）
     * @param lastCol 区域中最后一个单元格的列号
     * @param strings 下拉内容
     * @param hiddenSheet
     */
    public static void selectList(Workbook workbook, String sheetName, int firstCol, int lastCol, String[] strings, int hiddenSheet){
        Sheet sheet = workbook.createSheet(sheetName);
        for (int i = 0, len = strings.length; i < len; i++) {
            sheet.createRow(i).createCell(0).setCellValue(strings[i]);
        }
        Name categoryName = workbook.createName();
        categoryName.setNameName(sheetName+"hidden");
        categoryName.setRefersToFormula(sheetName+"!$A$1:$A$" + strings.length);

        DVConstraint constraint = DVConstraint.createFormulaListConstraint(sheetName+"hidden");
        CellRangeAddressList addressList = new CellRangeAddressList(2,65535, firstCol, lastCol);
        HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, constraint);
        workbook.setSheetHidden(hiddenSheet, true);
        workbook.getSheetAt(0).addValidationData(dataValidation);
    };

    /**
     * 给指定Cell设置样式
     * @param workbook
     * @param cell
     */
    public static void setCellStyle(Workbook workbook, Cell cell) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setWrapText(true);
        Font font = workbook.createFont();
        // 字体加粗
        font.setBold(true);
        cellStyle.setFont(font);
        // 设置颜色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//必须设置此项，否则背景色不显示
        //cellStyle.setBorderRight(BorderStyle.THIN);//设置右边框
        cell.setCellStyle(cellStyle);
    }

}
