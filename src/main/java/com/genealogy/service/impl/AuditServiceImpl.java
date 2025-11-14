package com.genealogy.service.impl;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.AuditApplyDTO;
import com.genealogy.dto.AuditQueryDTO;
import com.genealogy.dto.AuditReviewDTO;
import com.genealogy.entity.AuditRecord;
import com.genealogy.query.AuditHistoryQuery;
import com.genealogy.dao.AuditRecordDao;
import com.genealogy.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * 审核服务实现类
 * 
 * @author xiongyou
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRecordDao auditRecordDao;

    @Override
    public RestResult<Long> applyAudit(AuditApplyDTO auditApplyDTO) {
        // 校验传入的表名和记录ID是否合法
        if (auditApplyDTO.getTableName() == null || auditApplyDTO.getTableName().trim().isEmpty()) {
            return RestResult.fail("参数不合法", null);
        }
        if (auditApplyDTO.getRecordId() == null) {
            return RestResult.fail("参数不合法", null);
        }

        // 检查该记录是否已存在审核记录
        Optional<AuditRecord> existingRecord = auditRecordDao.findByTableNameAndRecordId(
                auditApplyDTO.getTableName(), auditApplyDTO.getRecordId());
        if (existingRecord.isPresent()) {
            AuditRecord record = existingRecord.get();
            if (record.getAuditStatus() == 0) { // 如果状态为待审核，则提示已在审核中
                return RestResult.fail("该记录已在审核中", null);
            }
        }

        // 将审核申请信息保存至审核记录表
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setTableName(auditApplyDTO.getTableName());
        auditRecord.setRecordId(auditApplyDTO.getRecordId());
        auditRecord.setAuditStatus(0); // 设置为待审核状态
        auditRecord.setAuditOpinion(auditApplyDTO.getAuditOpinion());
        auditRecord.setAuditBy(auditApplyDTO.getAuditBy());
        auditRecord.setAuditTime(new Timestamp(System.currentTimeMillis()));
        auditRecord.setIsApproved(false);
        auditRecord.setIsDeleted(0);

        AuditRecord savedRecord = auditRecordDao.save(auditRecord);
        return RestResult.success("提交成功", savedRecord.getAuditId());
    }

    @Override
    public RestResult<Void> approveAudit(AuditReviewDTO auditReviewDTO) {
        // 根据审核ID查询审核记录是否存在
        Optional<AuditRecord> optionalRecord = auditRecordDao.findByAuditId(auditReviewDTO.getAuditId());
        if (!optionalRecord.isPresent()) {
            return RestResult.fail("审核记录不存在", null);
        }

        AuditRecord record = optionalRecord.get();
        // 判断审核状态是否为待审核
        if (record.getAuditStatus() != 0) {
            return RestResult.fail("该记录已审核", null);
        }

        // 更新审核记录状态为已通过
        record.setAuditStatus(1); // 已通过
        record.setAuditOpinion(auditReviewDTO.getAuditOpinion());
        record.setAuditTime(new Timestamp(System.currentTimeMillis()));
        record.setIsApproved(true);
        auditRecordDao.save(record);

        return RestResult.success("审核通过", null);
    }

    @Override
    public RestResult<Void> rejectAudit(AuditReviewDTO auditReviewDTO) {
        // 根据审核ID查询审核记录是否存在
        Optional<AuditRecord> optionalRecord = auditRecordDao.findByAuditId(auditReviewDTO.getAuditId());
        if (!optionalRecord.isPresent()) {
            return RestResult.fail("审核记录不存在", null);
        }

        AuditRecord record = optionalRecord.get();
        // 判断审核状态是否为待审核
        if (record.getAuditStatus() != 0) {
            return RestResult.fail("该记录已审核", null);
        }

        // 更新审核记录状态为未通过
        record.setAuditStatus(2); // 未通过
        record.setAuditOpinion(auditReviewDTO.getAuditOpinion());
        record.setAuditTime(new Timestamp(System.currentTimeMillis()));
        record.setIsApproved(false);
        auditRecordDao.save(record);

        return RestResult.success("审核拒绝", null);
    }

    @Override
    public RestResult<List<AuditRecord>> getPendingAudits(AuditQueryDTO auditQueryDTO) {
        // 校验分页参数是否合法
        if (auditQueryDTO.getPageIndex() <= 0 || auditQueryDTO.getPageSize() <= 0) {
            return RestResult.fail("参数不合法", null);
        }

        // 查询所有待审核记录
        List<AuditRecord> pendingAudits = auditRecordDao.findPendingAudits(0); // 0表示待审核状态
        return RestResult.success("查询成功", pendingAudits);
    }

    @Override
    public RestResult<List<AuditRecord>> getAuditHistory(AuditHistoryQuery query) {
        List<AuditRecord> historyList;
        if (query.getTableName() != null && query.getRecordId() != null) {
            historyList = auditRecordDao.findAuditHistory(query.getTableName(), query.getRecordId());
        } else {
            historyList = auditRecordDao.findAll(); // 如果没有条件，则返回所有记录
        }
        return RestResult.success("查询成功", historyList);
    }
}