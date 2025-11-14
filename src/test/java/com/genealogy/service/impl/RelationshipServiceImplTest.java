package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RelationshipDTO;
import com.genealogy.entity.Relationship;
import com.genealogy.query.RelationshipQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.RelationshipDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * <p>
 *   亲属关系服务实现类单元测试
 * </p>
 * @author xiongyou
 */
public class RelationshipServiceImplTest {

    @Mock
    private RelationshipDao relationshipDao;

    @Mock
    private FamilyMemberDao familyMemberDao;

    @InjectMocks
    private RelationshipServiceImpl relationshipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRelationship_InvalidMemberId() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setCreateBy("test");

        when(familyMemberDao.existsById(1L)).thenReturn(false);
        when(familyMemberDao.existsById(2L)).thenReturn(true);

        RestResult<Long> result = relationshipService.addRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000001, result.getMsg());
    }

    @Test
    public void testAddRelationship_IllegalRelationType() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("非法类型");
        relationshipDTO.setCreateBy("test");

        when(familyMemberDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.existsById(2L)).thenReturn(true);

        RestResult<Long> result = relationshipService.addRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000002, result.getMsg());
    }

    @Test
    public void testAddRelationship_DuplicateRelationship() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setCreateBy("test");

        when(familyMemberDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.existsById(2L)).thenReturn(true);
        when(relationshipDao.findByFromMemberIdAndToMemberIdAndIsDeleted(1L, 2L, 0)).thenReturn(Optional.of(new Relationship()));

        RestResult<Long> result = relationshipService.addRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000003, result.getMsg());
    }

    @Test
    public void testAddRelationship_Success() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setCreateBy("test");

        when(familyMemberDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.existsById(2L)).thenReturn(true);
        when(relationshipDao.findByFromMemberIdAndToMemberIdAndIsDeleted(1L, 2L, 0)).thenReturn(Optional.empty());

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.save(any(Relationship.class))).thenReturn(relationship);

        RestResult<Long> result = relationshipService.addRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000000, result.getMsg());
        assertEquals(Long.valueOf(1L), result.getData());
    }

    @Test
    public void testDeleteRelationship_RelationshipNotFound() {
        RelationshipQuery relationshipQuery = new RelationshipQuery();
        relationshipQuery.setRelationId(1L);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<Void> result = relationshipService.deleteRelationship(relationshipQuery);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000004, result.getMsg());
    }

    @Test
    public void testDeleteRelationship_Success() {
        RelationshipQuery relationshipQuery = new RelationshipQuery();
        relationshipQuery.setRelationId(1L);

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(relationship));

        RestResult<Void> result = relationshipService.deleteRelationship(relationshipQuery);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000000, result.getMsg());
        verify(relationshipDao, times(1)).save(any(Relationship.class));
    }

    @Test
    public void testUpdateRelationship_RelationshipNotFound() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setRelationId(1L);
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setUpdateBy("test");

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<Void> result = relationshipService.updateRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000004, result.getMsg());
    }

    @Test
    public void testUpdateRelationship_InvalidMemberId() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setRelationId(1L);
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setUpdateBy("test");

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(relationship));
        when(familyMemberDao.existsById(1L)).thenReturn(false);
        when(familyMemberDao.existsById(2L)).thenReturn(true);

        RestResult<Void> result = relationshipService.updateRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000001, result.getMsg());
    }

    @Test
    public void testUpdateRelationship_IllegalRelationType() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setRelationId(1L);
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("非法类型");
        relationshipDTO.setUpdateBy("test");

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(relationship));
        when(familyMemberDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.existsById(2L)).thenReturn(true);

        RestResult<Void> result = relationshipService.updateRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000002, result.getMsg());
    }

    @Test
    public void testUpdateRelationship_Success() {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setRelationId(1L);
        relationshipDTO.setFromMemberId(1L);
        relationshipDTO.setToMemberId(2L);
        relationshipDTO.setRelationType("父子");
        relationshipDTO.setUpdateBy("test");

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(relationship));
        when(familyMemberDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.existsById(2L)).thenReturn(true);

        RestResult<Void> result = relationshipService.updateRelationship(relationshipDTO);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000000, result.getMsg());
        verify(relationshipDao, times(1)).save(any(Relationship.class));
    }

    @Test
    public void testListRelationships_Success() {
        RelationshipQuery relationshipQuery = new RelationshipQuery();
        relationshipQuery.setFromMemberId(1L);
        relationshipQuery.setToMemberId(2L);
        relationshipQuery.setRelationType("父子");

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByConditions(1L, 2L, "父子")).thenReturn(List.of(relationship));

        RestResult<List<RelationshipDTO>> result = relationshipService.listRelationships(relationshipQuery);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000000, result.getMsg());
        assertEquals(1, result.getData().size());
    }

    @Test
    public void testGetRelationship_RelationshipNotFound() {
        RelationshipQuery relationshipQuery = new RelationshipQuery();
        relationshipQuery.setRelationId(1L);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<RelationshipDTO> result = relationshipService.getRelationship(relationshipQuery);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000004, result.getMsg());
    }

    @Test
    public void testGetRelationship_Success() {
        RelationshipQuery relationshipQuery = new RelationshipQuery();
        relationshipQuery.setRelationId(1L);

        Relationship relationship = new Relationship();
        relationship.setRelationId(1L);
        relationship.setFromMemberId(1L);
        relationship.setToMemberId(2L);
        relationship.setRelationType("父子");
        relationship.setNote(null);
        relationship.setCreateBy("test");
        relationship.setCreateTime(LocalDateTime.now());
        relationship.setUpdateBy("test");
        relationship.setUpdateTime(LocalDateTime.now());
        relationship.setIsDeleted(0);

        when(relationshipDao.findByRelationIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(relationship));

        RestResult<RelationshipDTO> result = relationshipService.getRelationship(relationshipQuery);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.MSG_000000, result.getMsg());
        assertNotNull(result.getData());
    }
}