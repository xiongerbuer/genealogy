package com.genealogy.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PopulationReportDTO {

    @NotNull(message = "族谱树ID不能为空")
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;

    @NotNull(message = "报告名称不能为空")
    @ApiModelProperty(value = "报告名称")
    private String reportName;
}