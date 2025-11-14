package com.genealogy.dao;

import com.genealogy.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *   操作日志数据访问层
 * </p>
 * @author xiongyou
 */
public interface OperationLogDao extends JpaRepository<OperationLog, Long> {

    /**
     * 根据条件查询操作日志
     * @param userId 用户ID
     * @param module 模块名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 操作日志列表
     */
    @Query("SELECT ol FROM OperationLog ol WHERE (:userId IS NULL OR ol.userId = :userId) AND (:module IS NULL OR ol.operationModule = :module) AND ol.operationTime BETWEEN :startTime AND :endTime")
    List<OperationLog> findByConditions(@Param("userId") Long userId, @Param("module") String module, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}