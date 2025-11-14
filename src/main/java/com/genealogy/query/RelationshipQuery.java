package com.genealogy.query;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   亲属关系查询对象
 * </p>
 * @author xiongyou
 */
@Data
public class RelationshipQuery {

    /**
     * 关系ID
     */
    @NotNull(message = "关系ID不能为空")
    private Long relationId;

    /**
     * 起始成员ID
     */
    private Long fromMemberId;

    /**
     * 目标成员ID
     */
    private Long toMemberId;

    /**
     * 关系类型
     */
    private String relationType;
}