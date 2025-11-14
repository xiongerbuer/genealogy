package com.genealogy.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 家族查询条件
 * 
 * @author xiongyou
 */
@Data
@ApiModel("家族查询条件")
public class FamilyQuery {

    /**
     * 族谱树ID
     */
    @NotNull(message = "族谱树ID不能为空")
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;
}