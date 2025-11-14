package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.VersionHistoryDetailDTO;
import com.genealogy.query.VersionHistoryQuery;
import java.util.List;

/**
 * 版本历史服务接口
 * 
 * @author xiongyou
 */
public interface VersionHistoryService {

    /**
     * 查询版本历史列表
     * 
     * @param query 查询参数
     * @return 版本历史列表
     */
    RestResult<List<VersionHistoryDetailDTO>> listVersionHistory(VersionHistoryQuery query);

    /**
     * 查看指定版本详情
     * 
     * @param query 查询参数
     * @return 版本详情
     */
    RestResult<VersionHistoryDetailDTO> getVersionHistoryDetail(VersionHistoryQuery query);

    /**
     * 回滚至指定版本
     * 
     * @param query 查询参数
     * @return 回滚结果
     */
    RestResult<String> rollbackToVersion(VersionHistoryQuery query);
}