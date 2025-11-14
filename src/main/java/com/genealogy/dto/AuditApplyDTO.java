package com.genealogy.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 提交审核申请数据传输对象
 * 
 * @author xiongyou
 */
@Data
public class AuditApplyDTO {

    /**
     * 审核表名
     */
    @ApiModelProperty(value = "审核表名")
    @NotBlank(message = "表名不能为空")
    private String tableName;

    /**
     * 记录ID
     */
    @NotNull(message = "记录ID不能为空")
    private Long recordId;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核人
     */
    private String auditBy;
}