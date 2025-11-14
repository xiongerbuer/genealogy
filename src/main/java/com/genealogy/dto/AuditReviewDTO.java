package com.genealogy.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 审核操作数据传输对象
 * 
 * @author xiongyou
 */
@Data
public class AuditReviewDTO {

    /**
     * 审核ID
     */
    @NotNull(message = "审核ID不能为空")
    private Long auditId;

    /**
     * 审核意见
     */
    private String auditOpinion;
}