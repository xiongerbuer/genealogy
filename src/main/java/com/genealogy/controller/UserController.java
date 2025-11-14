package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.service.OperationLogService;
import com.genealogy.service.UserPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   用户权限控制配置
 * </p>
 * @author xiongyou
 */
@Api("用户权限控制配置")
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 配置用户权限
     *
     * @param userPermissionDTO 用户权限配置DTO
     * @return RestResult结果
     */
    @PostMapping("/configurePermissions")
    @ApiOperation("配置用户权限")
    public RestResult<Void> configureUserPermissions(@RequestBody @Validated UserPermissionDTO userPermissionDTO) {
        operationLogService.recordOperationLog(userPermissionDTO);
        return userPermissionService.configureUserPermissions(userPermissionDTO);
    }

    /**
     * 配置数据加密存储
     *
     * @param userPermissionDTO 数据加密存储配置DTO
     * @return RestResult结果
     */
    @PostMapping("/configureEncryption")
    @ApiOperation("配置数据加密存储")
    public RestResult<Void> configureDataEncryption(@RequestBody @Validated UserPermissionDTO userPermissionDTO) {
        operationLogService.recordOperationLog(userPermissionDTO);
        return userPermissionService.configureDataEncryption(userPermissionDTO);
    }

    /**
     * 设置数据访问权限
     *
     * @param userPermissionDTO 数据访问权限设置DTO
     * @return RestResult结果
     */
    @PostMapping("/setAccessPermissions")
    @ApiOperation("设置数据访问权限")
    public RestResult<Void> setDataAccessPermissions(@RequestBody @Validated UserPermissionDTO userPermissionDTO) {
        operationLogService.recordOperationLog(userPermissionDTO);
        return userPermissionService.setDataAccessPermissions(userPermissionDTO);
    }

}