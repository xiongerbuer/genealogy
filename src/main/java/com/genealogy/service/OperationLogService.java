package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.query.OperationLogQuery;
import java.util.List;

/**
 * <p>
 *   操作日志服务层接口
 * </p>
 * @author xiongyou
 */
public interface OperationLogService {

    /**
     * 查询操作日志
     * @param operationLogQuery 操作日志查询条件
     * @return RestResult结果
     */
    RestResult<List<UserPermissionDTO>> queryOperationLogs(OperationLogQuery operationLogQuery);

    /**
     * 记录操作日志
     * @param userPermissionDTO 操作日志记录DTO
     * @return RestResult结果
     */
    RestResult<Void> recordOperationLog(UserPermissionDTO userPermissionDTO);

}