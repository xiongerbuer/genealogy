package com.genealogy.query;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   多媒体资源查询对象
 * </p>
 * @author xiongyou
 */
@Data
public class MediaResourceQuery {

    /**
     * 成员ID
     */
    @NotNull(message = "成员ID不能为空")
    @ApiModelProperty(value = "成员ID")
    private Long memberId;

    /**
     * 资源ID
     */
    @NotNull(message = "资源ID不能为空")
    @ApiModelProperty(value = "资源ID")
    private Long resourceId;
}