package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.GenerationalReportDTO;
import com.genealogy.dto.PopulationReportDTO;
import com.genealogy.dto.ReportQuery;
import com.genealogy.entity.StatisticsReport;
import java.util.List;

public interface StatisticsReportService {

    RestResult<Long> generatePopulationReport(PopulationReportDTO reportDTO);

    RestResult<Long> generateGenerationalReport(GenerationalReportDTO reportDTO);

    RestResult<List<StatisticsReport>> getReportList(ReportQuery query);

    RestResult<Void> deleteReport(Long reportId);
}