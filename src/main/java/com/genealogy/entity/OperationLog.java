package com.genealogy.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <p>
 *   操作日志实体类
 * </p>
 * @author xiongyou
 */
@Data
@Entity
@Table(name = "operation_log")
public class OperationLog {

    /**
     * 日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 操作用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 操作模块
     */
    @Column(name = "operation_module")
    private String operationModule;

    /**
     * 操作类型
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * 操作描述
     */
    @Column(name = "operation_desc")
    private String operationDesc;

    /**
     * 操作时间
     */
    @Column(name = "operation_time")
    private LocalDateTime operationTime;

    /**
     * IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 浏览器标识
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;

}