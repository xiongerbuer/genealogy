package com.genealogy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   用户角色分配参数对象
 * </p>
 * @author xiongyou
 */
@Data
@ApiModel(description = "用户角色分配参数对象")
public class UserRoleAssignDTO {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
}