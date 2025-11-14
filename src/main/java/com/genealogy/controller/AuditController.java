package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.AuditApplyDTO;
import com.genealogy.dto.AuditQueryDTO;
import com.genealogy.dto.AuditReviewDTO;
import com.genealogy.entity.AuditRecord;
import com.genealogy.query.AuditHistoryQuery;
import com.genealogy.service.AuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 审核管理控制器
 * 
 * @author xiongyou
 */
@Api(tags = "信息审核管理")
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    /**
     * 提交审核申请
     * @param auditApplyDTO 审核申请信息
     * @return 审核结果
     */
    @PostMapping("/apply")
    @ApiOperation("提交审核申请")
    public RestResult<Long> applyAudit(@Valid @RequestBody AuditApplyDTO auditApplyDTO) {
        return auditService.applyAudit(auditApplyDTO);
    }

    /**
     * 审核通过
     * @param auditReviewDTO 审核信息
     * @return 审核结果
     */
    @PostMapping("/approve")
    @ApiOperation("审核通过")
    public RestResult<Void> approveAudit(@Valid @RequestBody AuditReviewDTO auditReviewDTO) {
        return auditService.approveAudit(auditReviewDTO);
    }

    /**
     * 审核拒绝
     * @param auditReviewDTO 审核信息
     * @return 审核结果
     */
    @PostMapping("/reject")
    @ApiOperation("审核拒绝")
    public RestResult<Void> rejectAudit(@Valid @RequestBody AuditReviewDTO auditReviewDTO) {
        return auditService.rejectAudit(auditReviewDTO);
    }

    /**
     * 查询待审核列表
     * @param auditQueryDTO 查询参数
     * @return 审核列表
     */
    @GetMapping("/pending")
    @ApiOperation("查询待审核列表")
    public RestResult<List<AuditRecord>> getPendingAudits(@Validated AuditQueryDTO auditQueryDTO) {
        return auditService.getPendingAudits(auditQueryDTO);
    }

    /**
     * 查询审核历史
     * @param query 查询参数
     * @return 审核历史
     */
    @GetMapping("/history")
    @ApiOperation("查询审核历史")
    public RestResult<List<AuditRecord>> getAuditHistory(AuditHistoryQuery query) {
        return auditService.getAuditHistory(query);
    }
}