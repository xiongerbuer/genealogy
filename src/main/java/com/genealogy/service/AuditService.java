package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.AuditApplyDTO;
import com.genealogy.dto.AuditQueryDTO;
import com.genealogy.dto.AuditReviewDTO;
import com.genealogy.entity.AuditRecord;
import com.genealogy.query.AuditHistoryQuery;

import java.util.List;

/**
 * 审核服务接口
 * 
 * @author xiongyou
 */
public interface AuditService {

    /**
     * 提交审核申请
     * @param auditApplyDTO 审核申请信息
     * @return 审核结果
     */
    RestResult<Long> applyAudit(AuditApplyDTO auditApplyDTO);

    /**
     * 审核通过
     * @param auditReviewDTO 审核信息
     * @return 审核结果
     */
    RestResult<Void> approveAudit(AuditReviewDTO auditReviewDTO);

    /**
     * 审核拒绝
     * @param auditReviewDTO 审核信息
     * @return 审核结果
     */
    RestResult<Void> rejectAudit(AuditReviewDTO auditReviewDTO);

    /**
     * 查询待审核列表
     * @param auditQueryDTO 查询参数
     * @return 审核列表
     */
    RestResult<List<AuditRecord>> getPendingAudits(AuditQueryDTO auditQueryDTO);

    /**
     * 查询审核历史
     * @param query 查询参数
     * @return 审核历史
     */
    RestResult<List<AuditRecord>> getAuditHistory(AuditHistoryQuery query);
}