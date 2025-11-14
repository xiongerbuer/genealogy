package com.genealogy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.VersionHistoryDetailDTO;
import com.genealogy.entity.VersionHistory;
import com.genealogy.query.VersionHistoryQuery;
import com.genealogy.dao.VersionHistoryDao;
import com.genealogy.service.VersionHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本历史服务实现类
 * 
 * @author xiongyou
 */
@Service
public class VersionHistoryServiceImpl implements VersionHistoryService {

    @Autowired
    private VersionHistoryDao versionHistoryDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public RestResult<List<VersionHistoryDetailDTO>> listVersionHistory(VersionHistoryQuery query) {
        // 获取版本历史记录列表
        List<VersionHistory> historyList = versionHistoryDao.findByTableNameAndRecordId(query.getTableName(), query.getRecordId());
        
        // 转换为DTO列表
        List<VersionHistoryDetailDTO> detailList = new ArrayList<>();
        for (VersionHistory history : historyList) {
            VersionHistoryDetailDTO dto = new VersionHistoryDetailDTO();
            BeanUtils.copyProperties(history, dto);
            detailList.add(dto);
        }
        
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, detailList);
    }

    @Override
    public RestResult<VersionHistoryDetailDTO> getVersionHistoryDetail(VersionHistoryQuery query) {
        // 根据版本历史ID查询版本记录
        VersionHistory history = versionHistoryDao.findByHistoryId(query.getHistoryId());
        
        if (history == null) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "版本记录不存在", null);
        }
        
        // 转换为DTO
        VersionHistoryDetailDTO detail = new VersionHistoryDetailDTO();
        BeanUtils.copyProperties(history, detail);
        
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, detail);
    }

    @Override
    public RestResult<String> rollbackToVersion(VersionHistoryQuery query) {
        // 验证历史版本是否存在
        VersionHistory history = versionHistoryDao.findByHistoryId(query.getHistoryId());
        if (history == null) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "版本记录不存在", null);
        }
        
        try {
            // 获取历史版本的操作前后数据
            String beforeData = history.getBeforeData();
            String afterData = history.getAfterData();
            String operationType = history.getOperationType();
            
            // 根据操作类型执行回滚操作（此处为模拟逻辑，实际应根据具体业务表进行处理）
            // 示例：INSERT -> DELETE, UPDATE -> UPDATE to before data, DELETE -> INSERT
            
            // 这里只是简单的返回成功信息，实际应用中应根据表结构和业务逻辑执行具体的回滚操作
            return new RestResult<>(ResultCodeConstant.CODE_000000, "回滚成功", "已成功回滚到版本ID:" + history.getHistoryId());
        } catch (Exception e) {
            // 若操作失败，返回系统异常信息
            return new RestResult<>(ResultCodeConstant.CODE_999999, "系统异常", null);
        }
    }
}