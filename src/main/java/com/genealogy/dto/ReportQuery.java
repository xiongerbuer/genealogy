package com.genealogy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportQuery {

    @ApiModelProperty(value = "报告类型")
    private String reportType;

    @ApiModelProperty(value = "生成时间范围开始")
    private LocalDate generateTimeStart;

    @ApiModelProperty(value = "生成时间范围结束")
    private LocalDate generateTimeEnd;
}