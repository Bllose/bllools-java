package com.bllools.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class Developer {

    @ExcelProperty("*账号")
    private String w3;

    @ExcelProperty("*姓名")
    private String name;

    @ExcelProperty("*员工属性")
    private String type;

    @ExcelProperty("四级团队")
    private String team4;

    @ExcelProperty("*工作状态")
    private String state;

    @ExcelProperty("*是否编码")
    private String isDev;

}
