package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RelationshipDTO;
import com.genealogy.entity.Relationship;
import com.genealogy.query.RelationshipQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.RelationshipDao;
import com.genealogy.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *   亲属关系服务实现类
 * </p>
 * @author xiongyou
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipDao relationshipDao;

    @Autowired
    private FamilyMemberDao familyMemberDao;

    @Override
    @Transactional
    public RestResult<Long> addRelationship(RelationshipDTO relationshipDTO) {
        // 校验起始成员ID和目标成员ID是否有效
        if (!familyMemberDao.existsById(relationshipDTO.getFromMemberId()) || !familyMemberDao.existsById(relationshipDTO.getToMemberId())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000001);
        }

        // 校验关系类型是否合法
        if (!isValidRelationType(relationshipDTO.getRelationType())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000002);
        }

        // 检查是否存在重复的关系记录
        Optional<Relationship> existingRelationship = relationshipDao.findByFromMemberIdAndToMemberIdAndIsDeleted(
                relationshipDTO.getFromMemberId(), relationshipDTO.getToMemberId(), 0);
        if (existingRelationship.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000003);
        }

        // 将新的亲属关系信息保存到数据库
        Relationship relationship = new Relationship();
        relationship.setFromMemberId(relationshipDTO.getFromMemberId());
        relationship.setToMemberId(relationshipDTO.getToMemberId());
        relationship.setRelationType(relationshipDTO.getRelationType());
        relationship.setNote(relationshipDTO.getNote());
        relationship.setCreateBy(relationshipDTO.getCreateBy());
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy(relationshipDTO.getUpdateBy());
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        Relationship savedRelationship = relationshipDao.save(relationship);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.MSG_000000, savedRelationship.getRelationId());
    }

    @Override
    @Transactional
    public RestResult<Void> deleteRelationship(RelationshipQuery relationshipQuery) {
        // 校验关系ID是否存在
        Optional<Relationship> relationshipOptional = relationshipDao.findByRelationIdAndIsDeleted(
                relationshipQuery.getRelationId(), 0);
        if (!relationshipOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000004);
        }

        // 标记该关系为删除状态
        Relationship relationship = relationshipOptional.get();
        relationship.setIsDeleted(1);
        relationship.setUpdateTime(LocalDateTime.now());
        relationshipDao.save(relationship);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.MSG_000000);
    }

    @Override
    @Transactional
    public RestResult<Void> updateRelationship(RelationshipDTO relationshipDTO) {
        // 校验关系ID是否存在
        Optional<Relationship> relationshipOptional = relationshipDao.findByRelationIdAndIsDeleted(
                relationshipDTO.getRelationId(), 0);
        if (!relationshipOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000004);
        }

        // 校验起始成员ID和目标成员ID是否有效
        if (!familyMemberDao.existsById(relationshipDTO.getFromMemberId()) || !familyMemberDao.existsById(relationshipDTO.getToMemberId())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000001);
        }

        // 校验关系类型是否合法
        if (!isValidRelationType(relationshipDTO.getRelationType())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000002);
        }

        // 更新指定的亲属关系信息
        Relationship relationship = relationshipOptional.get();
        relationship.setFromMemberId(relationshipDTO.getFromMemberId());
        relationship.setToMemberId(relationshipDTO.getToMemberId());
        relationship.setRelationType(relationshipDTO.getRelationType());
        relationship.setNote(relationshipDTO.getNote());
        relationship.setUpdateBy(relationshipDTO.getUpdateBy());
        relationship.setUpdateTime(LocalDateTime.now());
        relationshipDao.save(relationship);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.MSG_000000);
    }

    @Override
    public RestResult<List<RelationshipDTO>> listRelationships(RelationshipQuery relationshipQuery) {
        List<Relationship> relationships = relationshipDao.findByConditions(
                relationshipQuery.getFromMemberId(), relationshipQuery.getToMemberId(), relationshipQuery.getRelationType());
        List<RelationshipDTO> relationshipDTOs = relationships.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.MSG_000000, relationshipDTOs);
    }

    @Override
    public RestResult<RelationshipDTO> getRelationship(RelationshipQuery relationshipQuery) {
        // 校验关系ID是否存在
        Optional<Relationship> relationshipOptional = relationshipDao.findByRelationIdAndIsDeleted(
                relationshipQuery.getRelationId(), 0);
        if (!relationshipOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.MSG_000004);
        }

        // 获取并返回指定关系的详细信息
        Relationship relationship = relationshipOptional.get();
        RelationshipDTO relationshipDTO = convertToDTO(relationship);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.MSG_000000, relationshipDTO);
    }

    /**
     * 校验关系类型是否合法
     * @param relationType 关系类型
     * @return 是否合法
     */
    private boolean isValidRelationType(String relationType) {
        // TODO: 根据业务需求定义合法的关系类型
        return relationType.equals("父子") || relationType.equals("夫妻") || relationType.equals("兄弟");
    }

    /**
     * 将实体类转换为DTO
     * @param relationship 关系实体类
     * @return 关系DTO
     */
    private RelationshipDTO convertToDTO(Relationship relationship) {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setRelationId(relationship.getRelationId());
        relationshipDTO.setFromMemberId(relationship.getFromMemberId());
        relationshipDTO.setToMemberId(relationship.getToMemberId());
        relationshipDTO.setRelationType(relationship.getRelationType());
        relationshipDTO.setNote(relationship.getNote());
        relationshipDTO.setCreateBy(relationship.getCreateBy());
        relationshipDTO.setUpdateBy(relationship.getUpdateBy());
        return relationshipDTO;
    }
}