package com.genealogy.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   亲属关系数据传输对象
 * </p>
 * @author xiongyou
 */
@Data
public class RelationshipDTO {

    /**
     * 关系ID
     */
    @NotNull(message = "关系ID不能为空")
    private Long relationId;

    /**
     * 起始成员ID
     */
    @NotNull(message = "起始成员ID不能为空")
    private Long fromMemberId;

    /**
     * 目标成员ID
     */
    @NotNull(message = "目标成员ID不能为空")
    private Long toMemberId;

    /**
     * 关系类型
     */
    @NotNull(message = "关系类型不能为空")
    private String relationType;

    /**
     * 备注说明
     */
    private String note;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改人
     */
    private String updateBy;
}