package com.genealogy.query;

import lombok.Data;

/**
 * 版本历史查询参数对象
 * 
 * @author xiongyou
 */
@Data
public class VersionHistoryQuery {

    /**
     * 操作表名
     */
    private String tableName;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 页码
     */
    private Integer page = 1;

    /**
     * 每页条数
     */
    private Integer size = 10;

    /**
     * 版本历史ID（用于查看详情）
     */
    private Long historyId;
}