package com.genealogy.dao;

import com.genealogy.entity.DataImportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据导入日志仓库接口
 * 
 * @author xiongyou
 */
@Repository
public interface DataImportLogDao extends JpaRepository<DataImportLog, Long> {
}