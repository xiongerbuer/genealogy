package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;

/**
 * <p>
 *   用户权限配置服务层接口
 * </p>
 * @author xiongyou
 */
public interface UserPermissionService {

    /**
     * 配置用户权限
     * @param userPermissionDTO 用户权限配置DTO
     * @return RestResult结果
     */
    RestResult<Void> configureUserPermissions(UserPermissionDTO userPermissionDTO);

    /**
     * 配置数据加密存储
     * @param userPermissionDTO 数据加密存储配置DTO
     * @return RestResult结果
     */
    RestResult<Void> configureDataEncryption(UserPermissionDTO userPermissionDTO);

    /**
     * 设置数据访问权限
     * @param userPermissionDTO 数据访问权限设置DTO
     * @return RestResult结果
     */
    RestResult<Void> setDataAccessPermissions(UserPermissionDTO userPermissionDTO);

}