package com.genealogy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   用户状态更新参数对象
 * </p>
 * @author xiongyou
 */
@Data
@ApiModel(description = "用户状态更新参数对象")
public class UserStatusUpdateDTO {
    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    /**
     * 状态：1-启用，0-禁用
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态：1-启用，0-禁用", required = true)
    private Integer status;
}