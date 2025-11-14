package com.genealogy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 导出数据DTO
 * 
 * @author xiongyou
 */
@Data
@ApiModel("导出数据")
public class ExportDataDTO {

    /**
     * 导出类型
     */
    @NotBlank(message = "导出类型不能为空")
    @ApiModelProperty(value = "导出类型")
    private String exportType;

    /**
     * 族谱树ID
     */
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;
}