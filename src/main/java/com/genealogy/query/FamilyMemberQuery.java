package com.genealogy.query;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;

@Data
@ApiModel(description = "家族成员查询条件")
public class FamilyMemberQuery {

    /**
     * 关系类型
     */
    @ApiModelProperty(value = "关系类型")
    private String relationType;

    /**
     * 族谱树ID
     */
    @NotNull(message = "族谱树ID不能为空")
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;

    /**
     * 结束年份
     */
    @ApiModelProperty(value = "结束年份")
    private LocalDate endDate;

    /**
     * 成员姓名
     */
    @ApiModelProperty(value = "成员姓名")
    private String name;

    /**
     * 开始年份
     */
    @ApiModelProperty(value = "开始年份")
    private LocalDate startDate;

    /**
     * 成员ID
     */
    @NotNull(message = "成员ID不能为空")
    @ApiModelProperty(value = "成员ID")
    private Long memberId;
}
