package com.genealogy.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 *   用户权限配置DTO
 * </p>
 * @author xiongyou
 */
@Data
public class UserPermissionDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 角色ID列表
     */
    @NotNull(message = "角色ID列表不能为空")
    private List<Long> roleIds;

    /**
     * 资源ID
     */
    @NotNull(message = "资源ID不能为空")
    private Long resourceId;

    private String module;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    private String permission;

    private String desc;

    private String ipAddress;

    /**
     * 浏览器标识
     */
    private String userAgent;

    /**
     * 表名
     */
    @NotNull(message = "表名不能为空")
    private String tableName;

    /**
     * 列名
     */
    @NotNull(message = "列名不能为空")
    private String columnName;

    /**
     * 加密方式
     */
    private String encryptType;

}