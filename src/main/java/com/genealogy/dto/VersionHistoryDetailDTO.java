package com.genealogy.dto;

import java.sql.Timestamp;
import lombok.Data;

/**
 * 版本历史详情数据传输对象
 * 
 * @author xiongyou
 */
@Data
public class VersionHistoryDetailDTO {

    /**
     * 版本历史ID
     */
    private Long historyId;

    /**
     * 操作表名
     */
    private String tableName;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 操作类型（INSERT/UPDATE/DELETE）
     */
    private String operationType;

    /**
     * 操作前数据（JSON格式）
     */
    private String beforeData;

    /**
     * 操作后数据（JSON格式）
     */
    private String afterData;

    /**
     * 操作时间
     */
    private Timestamp operateTime;

    /**
     * 操作人
     */
    private String operatedBy;
}