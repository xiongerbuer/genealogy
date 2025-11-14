package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.query.OperationLogQuery;
import com.genealogy.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   操作日志查询
 * </p>
 * @author xiongyou
 */
@Api("操作日志查询")
@RequestMapping("operationLog")
@RestController
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询操作日志
     *
     * @param operationLogQuery 操作日志查询条件
     * @return RestResult结果
     */
    @GetMapping("/query")
    @ApiOperation("查询操作日志")
    public RestResult<List<UserPermissionDTO>> queryOperationLogs(@Validated OperationLogQuery operationLogQuery) {
        return operationLogService.queryOperationLogs(operationLogQuery);
    }

}