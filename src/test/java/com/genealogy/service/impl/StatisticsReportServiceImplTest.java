package com.genealogy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatisticsReportServiceImplTest {

    @Mock
    private FamilyTreeDao familyTreeDao;

    @Mock
    private FamilyMemberDao familyMemberDao;

    @Mock
    private StatisticsReportDao statisticsReportDao;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StatisticsReportServiceImpl statisticsReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGeneratePopulationReport_TreeExists() {
        Long treeId = 1L;
        String reportName = "Test Population Report";

        PopulationReportDTO reportDTO = new PopulationReportDTO();
        reportDTO.setTreeId(treeId);
        reportDTO.setReportName(reportName);

        FamilyTree familyTree = new FamilyTree();
        familyTree.setTreeId(treeId);

        when(familyTreeDao.findById(treeId)).thenReturn(Optional.of(familyTree));
        when(familyMemberDao.countByTreeIdAndGender(treeId, 1)).thenReturn(5L);
        when(familyMemberDao.countByTreeIdAndGender(treeId, 2)).thenReturn(3L);
        when(familyMemberDao.countByTreeId(treeId)).thenReturn(8L);

        RestResult<Long> result = statisticsReportService.generatePopulationReport(reportDTO);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
        verify(statisticsReportDao, times(1)).save(any(StatisticsReport.class));
    }

    @Test
    public void testGeneratePopulationReport_TreeDoesNotExist() {
        Long treeId = 1L;
        String reportName = "Test Population Report";

        PopulationReportDTO reportDTO = new PopulationReportDTO();
        reportDTO.setTreeId(treeId);
        reportDTO.setReportName(reportName);

        when(familyTreeDao.findById(treeId)).thenReturn(Optional.empty());

        RestResult<Long> result = statisticsReportService.generatePopulationReport(reportDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000001_MSG, result.getMsg());
        verify(statisticsReportDao, never()).save(any(StatisticsReport.class));
    }

    @Test
    public void testGenerateGenerationalReport_TreeExists() {
        Long treeId = 1L;
        String reportName = "Test Generational Report";

        GenerationalReportDTO reportDTO = new GenerationalReportDTO();
        reportDTO.setTreeId(treeId);
        reportDTO.setReportName(reportName);

        FamilyTree familyTree = new FamilyTree();
        familyTree.setTreeId(treeId);

        FamilyMember member1 = new FamilyMember();
        member1.setTreeId(treeId);
        member1.setBirthDate(new Date(120, 0, 1)); // 2020年

        FamilyMember member2 = new FamilyMember();
        member2.setTreeId(treeId);
        member2.setBirthDate(new Date(121, 0, 1)); // 2021年

        when(familyTreeDao.findById(treeId)).thenReturn(Optional.of(familyTree));
        when(familyMemberDao.findByTreeId(treeId)).thenReturn(List.of(member1, member2));

        RestResult<Long> result = statisticsReportService.generateGenerationalReport(reportDTO);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
        verify(statisticsReportDao, times(1)).save(any(StatisticsReport.class));
    }

    @Test
    public void testGenerateGenerationalReport_TreeDoesNotExist() {
        Long treeId = 1L;
        String reportName = "Test Generational Report";

        GenerationalReportDTO reportDTO = new GenerationalReportDTO();
        reportDTO.setTreeId(treeId);
        reportDTO.setReportName(reportName);

        when(familyTreeDao.findById(treeId)).thenReturn(Optional.empty());

        RestResult<Long> result = statisticsReportService.generateGenerationalReport(reportDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000001_MSG, result.getMsg());
        verify(statisticsReportDao, never()).save(any(StatisticsReport.class));
    }

    @Test
    public void testGetReportList_NoFilter() {
        ReportQuery query = new ReportQuery();

        List<StatisticsReport> reports = new ArrayList<>();
        reports.add(new StatisticsReport());
        reports.add(new StatisticsReport());

        when(statisticsReportDao.findReportsByCriteria(null, null, null)).thenReturn(reports);

        RestResult<List<StatisticsReport>> result = statisticsReportService.getReportList(query);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
        assertEquals(reports, result.getData());
    }

    @Test
    public void testDeleteReport_ReportExists() {
        Long reportId = 1L;

        StatisticsReport report = new StatisticsReport();
        report.setReportId(reportId);

        when(statisticsReportDao.findById(reportId)).thenReturn(Optional.of(report));

        RestResult<Void> result = statisticsReportService.deleteReport(reportId);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
        verify(statisticsReportDao, times(1)).save(report);
    }

    @Test
    public void testDeleteReport_ReportDoesNotExist() {
        Long reportId = 1L;

        when(statisticsReportDao.findById(reportId)).thenReturn(Optional.empty());

        RestResult<Void> result = statisticsReportService.deleteReport(reportId);

        assertEquals(ResultCodeConstant.CODE_000002, result.getCode());
        assertEquals("报告不存在", result.getMsg());
        verify(statisticsReportDao, never()).save(any(StatisticsReport.class));
    }
}