package com.genealogy.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Data;

/**
 * 版本历史记录实体类
 * 
 * @author xiongyou
 */
@Data
@Entity
@Table(name = "version_history")
public class VersionHistory {

    /**
     * 版本历史ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    /**
     * 操作表名
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 记录ID
     */
    @Column(name = "record_id")
    private Long recordId;

    /**
     * 操作类型（INSERT/UPDATE/DELETE）
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * 操作前数据（JSON格式）
     */
    @Column(name = "before_data", columnDefinition = "json")
    private String beforeData;

    /**
     * 操作后数据（JSON格式）
     */
    @Column(name = "after_data", columnDefinition = "json")
    private String afterData;

    /**
     * 操作时间
     */
    @Column(name = "operate_time")
    private Timestamp operateTime;

    /**
     * 操作人
     */
    @Column(name = "operated_by")
    private String operatedBy;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}