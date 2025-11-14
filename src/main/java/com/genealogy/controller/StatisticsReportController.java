package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.GenerationalReportDTO;
import com.genealogy.dto.PopulationReportDTO;
import com.genealogy.dto.ReportQuery;
import com.genealogy.entity.StatisticsReport;
import com.genealogy.service.StatisticsReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("统计分析管理")
@RequestMapping("statistics")
@RestController
public class StatisticsReportController {

    @Autowired
    private StatisticsReportService statisticsReportService;

    /**
     * 生成人口统计报告
     *
     * @param reportDTO 报告信息
     * @return 统计报告ID
     */
    @RequestMapping(value = "/population-report", method = RequestMethod.POST)
    @ApiOperation("生成人口统计报告")
    public RestResult<Long> generatePopulationReport(@RequestBody @Validated PopulationReportDTO reportDTO) {
        return statisticsReportService.generatePopulationReport(reportDTO);
    }

    /**
     * 生成代际分布分析报告
     *
     * @param reportDTO 报告信息
     * @return 统计报告ID
     */
    @RequestMapping(value = "/generational-report", method = RequestMethod.POST)
    @ApiOperation("生成代际分布分析报告")
    public RestResult<Long> generateGenerationalReport(@RequestBody @Validated GenerationalReportDTO reportDTO) {
        return statisticsReportService.generateGenerationalReport(reportDTO);
    }

    /**
     * 获取统计报告列表
     *
     * @param query 报告查询条件
     * @return 统计报告列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("获取统计报告列表")
    public RestResult<List<StatisticsReport>> getReportList(@ModelAttribute @Validated ReportQuery query) {
        return statisticsReportService.getReportList(query);
    }

    /**
     * 删除统计报告
     *
     * @param reportId 报告ID
     * @return 删除结果
     */
    @RequestMapping(value = "/delete/{reportId}", method = RequestMethod.DELETE)
    @ApiOperation("删除统计报告")
    public RestResult<Void> deleteReport(@PathVariable Long reportId) {
        return statisticsReportService.deleteReport(reportId);
    }
}