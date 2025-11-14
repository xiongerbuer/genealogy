package com.genealogy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 *   家族成员搜索条件
 * </p>
 * @author xiongyou
 */
@Data
@ApiModel(description = "家族成员搜索条件")
public class FamilyMemberSearchDTO {

    /**
     * 成员姓名
     */
    @ApiModelProperty(value = "成员姓名")
    private String name;

    /**
     * 关系类型
     */
    @ApiModelProperty(value = "关系类型")
    private String relationType;

    /**
     * 开始年份
     */
    @ApiModelProperty(value = "开始年份")
    private LocalDate startDate;

    /**
     * 结束年份
     */
    @ApiModelProperty(value = "结束年份")
    private LocalDate endDate;

    /**
     * 族谱树ID
     */
    @NotNull(message = "族谱树ID不能为空")
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;
}