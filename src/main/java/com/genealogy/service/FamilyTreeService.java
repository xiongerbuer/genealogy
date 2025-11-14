package com.genealogy.service;

import com.genealogy.dto.FamilyTreeDTO;
import com.genealogy.entity.FamilyTree;
import com.genealogy.query.FamilyTreeQuery;
import java.util.List;

/**
 * <p>
 *   族谱树服务层接口
 * </p>
 * @author xiongyou
 */
public interface FamilyTreeService {

    /**
     * 新建族谱树
     * @param familyTreeDTO 族谱树信息
     * @return 族谱树ID
     */
    Long createFamilyTree(FamilyTreeDTO familyTreeDTO);

    /**
     * 编辑族谱树
     * @param familyTreeDTO 族谱树信息
     * @return 是否成功
     */
    boolean updateFamilyTree(FamilyTreeDTO familyTreeDTO);

    /**
     * 删除族谱树
     * @param familyTreeQuery 族谱树查询条件
     * @return 是否成功
     */
    boolean deleteFamilyTree(FamilyTreeQuery familyTreeQuery);

    /**
     * 查询族谱树详情
     * @param familyTreeQuery 族谱树查询条件
     * @return 族谱树详情
     */
    FamilyTree getFamilyTreeDetails(FamilyTreeQuery familyTreeQuery);

    /**
     * 查询族谱树列表
     * @param treeName 族谱名称
     * @return 族谱树列表
     */
    List<FamilyTree> getFamilyTreeList(String treeName);
}