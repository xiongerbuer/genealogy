package com.genealogy.query;

import lombok.Data;

/**
 * 查询审核历史查询对象
 * 
 * @author xiongyou
 */
@Data
public class AuditHistoryQuery {

    /**
     * 审核表名
     */
    private String tableName;

    /**
     * 记录ID
     */
    private Long recordId;
}