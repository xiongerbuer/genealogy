package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.exception.BusinessException;
import com.genealogy.dto.FamilyTreeDTO;
import com.genealogy.entity.FamilyTree;
import com.genealogy.query.FamilyTreeQuery;
import com.genealogy.dao.FamilyTreeDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FamilyTreeServiceImplTest {

    @Mock
    private FamilyTreeDao familyTreeDao;

    @InjectMocks
    private FamilyTreeServiceImpl familyTreeService;

    private FamilyTreeDTO familyTreeDTO;
    private FamilyTreeQuery familyTreeQuery;
    private FamilyTree familyTree;

    @BeforeEach
    public void setUp() {
        familyTreeDTO = new FamilyTreeDTO();
        familyTreeDTO.setTreeName("Test Tree");
        familyTreeDTO.setDescription("This is a test tree");

        familyTreeQuery = new FamilyTreeQuery();
        familyTreeQuery.setTreeId(1L);

        familyTree = new FamilyTree();
        familyTree.setTreeId(1L);
        familyTree.setTreeName("Test Tree");
        familyTree.setDescription("This is a test tree");
        familyTree.setRootMemberId(100L);
        familyTree.setCreateBy("testUser");
        familyTree.setCreateTime(LocalDateTime.now());
        familyTree.setUpdateBy("testUser");
        familyTree.setUpdateTime(LocalDateTime.now());
        familyTree.setIsDeleted(0);
    }

    @Test
    public void testCreateFamilyTree_Success() {
        when(familyTreeDao.findByTreeName(anyString())).thenReturn(Optional.empty());
        Long treeId = familyTreeService.createFamilyTree(familyTreeDTO);
        assertNotNull(treeId);
        verify(familyTreeDao, times(1)).save(any(FamilyTree.class));
    }

    @Test
    public void testCreateFamilyTree_Failure() {
        when(familyTreeDao.findByTreeName(anyString())).thenReturn(Optional.of(familyTree));
        BusinessException exception = assertThrows(BusinessException.class, () -> familyTreeService.createFamilyTree(familyTreeDTO));
        assertEquals(ResultCodeConstant.CODE_000001, exception.getCode());
        assertEquals("族谱名称已存在", exception.getMessage());
        verify(familyTreeDao, never()).save(any(FamilyTree.class));
    }

    @Test
    public void testUpdateFamilyTree_Success() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.of(familyTree));
        boolean success = familyTreeService.updateFamilyTree(familyTreeDTO);
        assertTrue(success);
        verify(familyTreeDao, times(1)).save(any(FamilyTree.class));
    }

    @Test
    public void testUpdateFamilyTree_Failure() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> familyTreeService.updateFamilyTree(familyTreeDTO));
        assertEquals(ResultCodeConstant.CODE_000001, exception.getCode());
        assertEquals("族谱树不存在", exception.getMessage());
        verify(familyTreeDao, never()).save(any(FamilyTree.class));
    }

    @Test
    public void testDeleteFamilyTree_Success() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.of(familyTree));
        boolean success = familyTreeService.deleteFamilyTree(familyTreeQuery);
        assertTrue(success);
        verify(familyTreeDao, times(1)).save(any(FamilyTree.class));
    }

    @Test
    public void testDeleteFamilyTree_Failure() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> familyTreeService.deleteFamilyTree(familyTreeQuery));
        assertEquals(ResultCodeConstant.CODE_000001, exception.getCode());
        assertEquals("族谱树不存在", exception.getMessage());
        verify(familyTreeDao, never()).save(any(FamilyTree.class));
    }

    @Test
    public void testGetFamilyTreeDetails_Success() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.of(familyTree));
        FamilyTree result = familyTreeService.getFamilyTreeDetails(familyTreeQuery);
        assertNotNull(result);
        assertEquals(familyTree.getTreeId(), result.getTreeId());
        verify(familyTreeDao, times(1)).findById(anyLong());
    }

    @Test
    public void testGetFamilyTreeDetails_Failure() {
        when(familyTreeDao.findById(anyLong())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> familyTreeService.getFamilyTreeDetails(familyTreeQuery));
        assertEquals(ResultCodeConstant.CODE_000001, exception.getCode());
        assertEquals("族谱树不存在", exception.getMessage());
        verify(familyTreeDao, times(1)).findById(anyLong());
    }

    @Test
    public void testGetFamilyTreeList_Success() {
        when(familyTreeDao.findByTreeNameContainingAndIsDeleted(anyString(), anyInt())).thenReturn(List.of(familyTree));
        List<FamilyTree> result = familyTreeService.getFamilyTreeList("Test");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(familyTreeDao, times(1)).findByTreeNameContainingAndIsDeleted(anyString(), anyInt());
    }

    @Test
    public void testGetFamilyTreeList_NoFilter() {
        when(familyTreeDao.findByIsDeleted(anyInt())).thenReturn(List.of(familyTree));
        List<FamilyTree> result = familyTreeService.getFamilyTreeList(null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(familyTreeDao, times(1)).findByIsDeleted(anyInt());
    }
}