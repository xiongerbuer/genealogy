package com.genealogy.dto;

import com.genealogy.common.validation.CreateGroup;
import com.genealogy.common.validation.EditGroup;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *   族谱树数据传输对象
 * </p>
 * @author xiongyou
 */
public class FamilyTreeDTO {

    /**
     * 族谱树ID
     */
    @NotNull(groups = EditGroup.class, message = "族谱树ID不能为空")
    @ApiModelProperty(value = "族谱树ID")
    private Long treeId;

    /**
     * 族谱名称
     */
    @NotBlank(groups = {CreateGroup.class, EditGroup.class}, message = "族谱名称不能为空")
    @ApiModelProperty(value = "族谱名称")
    private String treeName;

    /**
     * 族谱描述
     */
    @ApiModelProperty(value = "族谱描述")
    private String description;

    // Getters and Setters
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}