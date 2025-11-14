package com.genealogy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;

@Data
@ApiModel(description = "家族成员信息")
public class FamilyMemberDTO {

    /**
     * 成员ID
     */
    @ApiModelProperty(value = "成员ID")
    private Long memberId;

    /**
     * 所属族谱树ID
     */
    @ApiModelProperty(value = "所属族谱树ID")
    private Long treeId;

    /**
     * 成员姓名
     */
    @ApiModelProperty(value = "成员姓名")
    private String name;

    /**
     * 性别：1-男，2-女，0-未知
     */
    @ApiModelProperty(value = "性别：1-男，2-女，0-未知")
    private Integer gender;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthDate;

    /**
     * 死亡日期
     */
    @ApiModelProperty(value = "死亡日期")
    private LocalDate deathDate;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public interface CreateGroup {
    }

    public interface UpdateGroup {
    }
}
