package com.genealogy.dao;

import com.genealogy.entity.VersionHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 版本历史记录数据访问层接口
 * 
 * @author xiongyou
 */
@Repository
public interface VersionHistoryDao extends JpaRepository<VersionHistory, Long> {

    /**
     * 根据表名和记录ID查询版本历史记录列表
     * 
     * @param tableName 表名
     * @param recordId 记录ID
     * @return 版本历史记录列表
     */
    @Query("SELECT vh FROM VersionHistory vh WHERE vh.tableName = :tableName AND vh.recordId = :recordId AND vh.isDeleted = 0 ORDER BY vh.operateTime DESC")
    List<VersionHistory> findByTableNameAndRecordId(@Param("tableName") String tableName, @Param("recordId") Long recordId);

    /**
     * 根据版本历史ID查询版本记录
     * 
     * @param historyId 版本历史ID
     * @return 版本历史记录
     */
    @Query("SELECT vh FROM VersionHistory vh WHERE vh.historyId = :historyId AND vh.isDeleted = 0")
    VersionHistory findByHistoryId(@Param("historyId") Long historyId);
}