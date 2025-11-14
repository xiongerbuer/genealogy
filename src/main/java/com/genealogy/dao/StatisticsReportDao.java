package com.genealogy.dao;

import com.genealogy.entity.StatisticsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsReportDao extends JpaRepository<StatisticsReport, Long> {

    @Query("SELECT s FROM StatisticsReport s WHERE (:reportType IS NULL OR s.reportType = :reportType) " +
           "AND (:generateTimeStart IS NULL OR s.generateTime >= :generateTimeStart) " +
           "AND (:generateTimeEnd IS NULL OR s.generateTime <= :generateTimeEnd)")
    List<StatisticsReport> findReportsByCriteria(@Param("reportType") String reportType,
                                                 @Param("generateTimeStart") LocalDate generateTimeStart,
                                                 @Param("generateTimeEnd") LocalDate generateTimeEnd);
}