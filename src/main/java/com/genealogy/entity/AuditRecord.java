package com.genealogy.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Data;

/**
 * 审核记录实体类
 * 
 * @author xiongyou
 */
@Data
@Entity
@Table(name = "audit_record")
public class AuditRecord {

    /**
     * 审核记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    /**
     * 审核表名
     */
    @Column(name = "table_name", nullable = false)
    private String tableName;

    /**
     * 记录ID
     */
    @Column(name = "record_id", nullable = false)
    private Long recordId;

    /**
     * 审核状态：0-待审核，1-已通过，2-未通过
     */
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    /**
     * 审核意见
     */
    @Column(name = "audit_opinion", columnDefinition = "TEXT")
    private String auditOpinion;

    /**
     * 审核人
     */
    @Column(name = "audit_by", nullable = false)
    private String auditBy;

    /**
     * 审核时间
     */
    @Column(name = "audit_time", nullable = false)
    private Timestamp auditTime;

    /**
     * 是否批准
     */
    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted;
}