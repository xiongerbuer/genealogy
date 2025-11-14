package com.genealogy.dao;

import com.genealogy.entity.AuditRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 审核记录数据访问层
 * 
 * @author xiongyou
 */
@Repository
public interface AuditRecordDao extends JpaRepository<AuditRecord, Long> {

    /**
     * 根据表名和记录ID查找审核记录
     * @param tableName 表名
     * @param recordId 记录ID
     * @return 审核记录
     */
    @Query("SELECT a FROM AuditRecord a WHERE a.tableName = :tableName AND a.recordId = :recordId AND a.isDeleted = 0")
    Optional<AuditRecord> findByTableNameAndRecordId(@Param("tableName") String tableName, @Param("recordId") Long recordId);

    /**
     * 根据审核ID查找审核记录
     * @param auditId 审核ID
     * @return 审核记录
     */
    @Query("SELECT a FROM AuditRecord a WHERE a.auditId = :auditId AND a.isDeleted = 0")
    Optional<AuditRecord> findByAuditId(@Param("auditId") Long auditId);

    /**
     * 查询待审核记录列表
     * @param auditStatus 审核状态
     * @return 待审核记录列表
     */
    @Query("SELECT a FROM AuditRecord a WHERE a.auditStatus = :auditStatus AND a.isDeleted = 0")
    List<AuditRecord> findPendingAudits(@Param("auditStatus") Integer auditStatus);

    /**
     * 根据表名和记录ID查询审核历史
     * @param tableName 表名
     * @param recordId 记录ID
     * @return 审核历史列表
     */
    @Query("SELECT a FROM AuditRecord a WHERE a.tableName = :tableName AND a.recordId = :recordId AND a.isDeleted = 0 ORDER BY a.auditTime DESC")
    List<AuditRecord> findAuditHistory(@Param("tableName") String tableName, @Param("recordId") Long recordId);
}