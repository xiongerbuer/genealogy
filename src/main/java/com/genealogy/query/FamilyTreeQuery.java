package com.genealogy.query;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *   族谱树查询对象
 * </p>
 * @author xiongyou
 */
public class FamilyTreeQuery {

    /**
     * 族谱树ID
     */
    @NotNull(message = "族谱树ID不能为空")
    private Long treeId;

    // Getters and Setters
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
}