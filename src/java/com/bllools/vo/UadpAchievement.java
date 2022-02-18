package com.bllools.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class UadpAchievement {

    @ExcelProperty("Requirement Type")
    private String requirementType;

    @ExcelProperty("Estimated KLOC")
    private Double estimated;

    @ExcelProperty("DE")
    private String de;

    @ExcelProperty("Develop Time")
    private String developTime;

    @ExcelIgnore
    private int week;
}
