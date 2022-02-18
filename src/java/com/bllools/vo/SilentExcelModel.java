package com.bllools.vo;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class SilentExcelModel {

    @ExcelProperty("工号")
    private String w3;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("类别")
    private String type;

    @ExcelProperty("组别")
    private String team4;

    @ExcelProperty("PL")
    private String pl;

    @ExcelProperty("达成周数")
    private int count;

    @ExcelProperty("第1周")
    private BigDecimal week1 = new BigDecimal("0.0");

    @ExcelProperty("第2周")
    private BigDecimal week2 = new BigDecimal("0.0");

    @ExcelProperty("第3周")
    private BigDecimal week3 = new BigDecimal("0.0");

    @ExcelProperty("第4周")
    private BigDecimal week4 = new BigDecimal("0.0");

    @ExcelProperty("第5周")
    private BigDecimal week5 = new BigDecimal("0.0");
}
