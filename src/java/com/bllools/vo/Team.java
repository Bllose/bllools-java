package com.bllools.vo;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class Team {

    @ExcelProperty("四级团队")
    private String team4;

    @ExcelProperty("PL")
    private String pl;
}
