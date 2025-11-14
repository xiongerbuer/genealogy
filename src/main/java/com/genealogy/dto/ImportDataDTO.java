package com.genealogy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 导入数据DTO
 * 
 * @author xiongyou
 */
@Data
@ApiModel("导入数据")
public class ImportDataDTO {

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空")
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 文件内容
     */
    @NotBlank(message = "文件内容不能为空")
    @ApiModelProperty(value = "文件内容")
    private String fileContent;
}