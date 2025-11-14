package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.exception.BusinessException;
import com.genealogy.dto.FamilyTreeDTO;
import com.genealogy.entity.FamilyTree;
import com.genealogy.query.FamilyTreeQuery;
import com.genealogy.dao.FamilyTreeDao;
import com.genealogy.service.FamilyTreeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   族谱树服务层实现
 * </p>
 * @author xiongyou
 */
@Service
public class FamilyTreeServiceImpl implements FamilyTreeService {

    @Autowired
    private FamilyTreeDao familyTreeDao;

    @Override
    @Transactional
    public Long createFamilyTree(FamilyTreeDTO familyTreeDTO) {
        Optional<FamilyTree> existingTreeOptional = familyTreeDao.findByTreeName(familyTreeDTO.getTreeName());
        if (existingTreeOptional.isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001, "族谱名称已存在");
        }
        FamilyTree familyTree = new FamilyTree();
        BeanUtils.copyProperties(familyTreeDTO, familyTree);
        familyTree.setCreateTime(LocalDateTime.now());
        familyTree.setUpdateTime(LocalDateTime.now());
        familyTree.setIsDeleted(0);
        FamilyTree result = familyTreeDao.save(familyTree);
        return result.getTreeId();
    }

    @Override
    @Transactional
    public boolean updateFamilyTree(FamilyTreeDTO familyTreeDTO) {
        Optional<FamilyTree> familyTreeOptional = familyTreeDao.findById(familyTreeDTO.getTreeId());
        if (!familyTreeOptional.isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001, "族谱树不存在");
        }
        FamilyTree familyTree = familyTreeOptional.get();
        familyTree.setTreeName(familyTreeDTO.getTreeName());
        familyTree.setDescription(familyTreeDTO.getDescription());
        familyTree.setUpdateTime(LocalDateTime.now());
        familyTreeDao.save(familyTree);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteFamilyTree(FamilyTreeQuery familyTreeQuery) {
        Optional<FamilyTree> familyTreeOptional = familyTreeDao.findById(familyTreeQuery.getTreeId());
        if (!familyTreeOptional.isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001, "族谱树不存在");
        }
        FamilyTree familyTree = familyTreeOptional.get();
        familyTree.setIsDeleted(1);
        familyTree.setUpdateTime(LocalDateTime.now());
        familyTreeDao.save(familyTree);
        return true;
    }

    @Override
    public FamilyTree getFamilyTreeDetails(FamilyTreeQuery familyTreeQuery) {
        Optional<FamilyTree> familyTreeOptional = familyTreeDao.findById(familyTreeQuery.getTreeId());
        if (!familyTreeOptional.isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001, "族谱树不存在");
        }
        return familyTreeOptional.get();
    }

    @Override
    public List<FamilyTree> getFamilyTreeList(String treeName) {
        if (treeName == null || treeName.isEmpty()) {
            return familyTreeDao.findAll();
        }
        return familyTreeDao.findByTreeNameContainingAndIsDeleted(treeName, 0);
    }
}