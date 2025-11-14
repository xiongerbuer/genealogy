package com.genealogy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.GenerationalReportDTO;
import com.genealogy.dto.PopulationReportDTO;
import com.genealogy.dto.ReportQuery;
import com.genealogy.entity.FamilyMember;
import com.genealogy.entity.FamilyTree;
import com.genealogy.entity.StatisticsReport;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.FamilyTreeDao;
import com.genealogy.dao.StatisticsReportDao;
import com.genealogy.service.StatisticsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StatisticsReportServiceImpl implements StatisticsReportService {

    @Autowired
    private FamilyTreeDao familyTreeDao;

    @Autowired
    private FamilyMemberDao familyMemberDao;

    @Autowired
    private StatisticsReportDao statisticsReportDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public RestResult<Long> generatePopulationReport(PopulationReportDTO reportDTO) {
        Optional<FamilyTree> familyTreeOptional = familyTreeDao.findById(reportDTO.getTreeId());
        if (!familyTreeOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG, null);
        }

        long maleCount = familyMemberDao.countByTreeIdAndGender(reportDTO.getTreeId(), 1);
        long femaleCount = familyMemberDao.countByTreeIdAndGender(reportDTO.getTreeId(), 2);
        long totalCount = familyMemberDao.countByTreeId(reportDTO.getTreeId());

        ObjectNode content = objectMapper.createObjectNode();
        content.put("maleCount", maleCount);
        content.put("femaleCount", femaleCount);
        content.put("totalCount", totalCount);

        StatisticsReport report = new StatisticsReport();
        report.setReportName(reportDTO.getReportName());
        report.setReportType("population");
        report.setGenerateTime(LocalDateTime.now());
        report.setGeneratedBy("system");
        report.setContent(objectMapper.valueToTree(content));

        StatisticsReport savedReport = statisticsReportDao.save(report);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, savedReport.getReportId());
    }

    @Override
    @Transactional
    public RestResult<Long> generateGenerationalReport(GenerationalReportDTO reportDTO) {
        Optional<FamilyTree> familyTreeOptional = familyTreeDao.findById(reportDTO.getTreeId());
        if (!familyTreeOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG, null);
        }

        List<FamilyMember> members = familyMemberDao.findByTreeId(reportDTO.getTreeId());
        Map<Integer, Integer> generationCountMap = new HashMap<>();

        for (FamilyMember member : members) {
            if (member.getBirthDate() != null) {
                int generation = calculateGeneration(member.getBirthDate());
                generationCountMap.put(generation, generationCountMap.getOrDefault(generation, 0) + 1);
            }
        }

        ObjectNode content = objectMapper.createObjectNode();
        generationCountMap.forEach((key, value) -> content.put(String.valueOf(key), value.intValue()));


        StatisticsReport report = new StatisticsReport();
        report.setReportName(reportDTO.getReportName());
        report.setReportType("generational");
        report.setGenerateTime(LocalDateTime.now());
        report.setGeneratedBy("system");
        report.setContent(objectMapper.valueToTree(content));

        StatisticsReport savedReport = statisticsReportDao.save(report);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, savedReport.getReportId());
    }

    @Override
    public RestResult<List<StatisticsReport>> getReportList(ReportQuery query) {
        List<StatisticsReport> reports = statisticsReportDao.findReportsByCriteria(query.getReportType(),
                                                                                         query.getGenerateTimeStart(),
                                                                                         query.getGenerateTimeEnd());
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, reports);
    }

    @Override
    @Transactional
    public RestResult<Void> deleteReport(Long reportId) {
        Optional<StatisticsReport> reportOptional = statisticsReportDao.findById(reportId);
        if (!reportOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "报告不存在", null);
        }

        StatisticsReport report = reportOptional.get();

        statisticsReportDao.deleteById(report.getReportId());
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", null);
    }

    private int calculateGeneration(LocalDate birthDate) {
        // 简单示例，根据出生日期计算代际
        // 实际应用中可能需要更复杂的逻辑
        return birthDate.getYear() - 1900;
    }
}