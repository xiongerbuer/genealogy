package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.FamilyMemberDTO;
import com.genealogy.entity.FamilyMember;
import com.genealogy.query.FamilyMemberQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.FamilyTreeDao;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FamilyMemberServiceImplTest {

    @Mock
    private FamilyMemberDao familyMemberDao;

    @Mock
    private FamilyTreeDao familyTreeDao;

    @InjectMocks
    private FamilyMemberServiceImpl familyMemberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFamilyMember_Success() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setName("张三");
        familyMemberDTO.setGender(1);

        when(familyTreeDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.save(any(FamilyMember.class))).thenReturn(new FamilyMember());

        RestResult<Long> result = familyMemberService.addFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        verify(familyMemberDao, times(1)).save(any(FamilyMember.class));
    }

    @Test
    public void testAddFamilyMember_FamilyTreeNotFound() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setName("张三");
        familyMemberDTO.setGender(1);

        when(familyTreeDao.existsById(1L)).thenReturn(false);

        RestResult<Long> result = familyMemberService.addFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("族谱树不存在", result.getMsg());
        verify(familyMemberDao, times(0)).save(any(FamilyMember.class));
    }

    @Test
    public void testAddFamilyMember_NameIsNull() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setGender(1);

        when(familyTreeDao.existsById(1L)).thenReturn(true);

        RestResult<Long> result = familyMemberService.addFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员姓名不能为空", result.getMsg());
        verify(familyMemberDao, times(0)).save(any(FamilyMember.class));
    }

    @Test
    public void testUpdateFamilyMember_Success() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setMemberId(1L);
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setName("张三");
        familyMemberDTO.setGender(1);

        FamilyMember familyMember = new FamilyMember();
        familyMember.setMemberId(1L);
        familyMember.setIsDeleted(0);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(familyMember));
        when(familyTreeDao.existsById(1L)).thenReturn(true);
        when(familyMemberDao.save(any(FamilyMember.class))).thenReturn(familyMember);

        RestResult<Void> result = familyMemberService.updateFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        verify(familyMemberDao, times(1)).save(any(FamilyMember.class));
    }

    @Test
    public void testUpdateFamilyMember_FamilyMemberNotFound() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setMemberId(1L);
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setName("张三");
        familyMemberDTO.setGender(1);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<Void> result = familyMemberService.updateFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员信息不存在", result.getMsg());
        verify(familyMemberDao, times(0)).save(any(FamilyMember.class));
    }

    @Test
    public void testUpdateFamilyMember_FamilyTreeNotFound() {
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setMemberId(1L);
        familyMemberDTO.setTreeId(1L);
        familyMemberDTO.setName("张三");
        familyMemberDTO.setGender(1);

        FamilyMember familyMember = new FamilyMember();
        familyMember.setMemberId(1L);
        familyMember.setIsDeleted(0);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(familyMember));
        when(familyTreeDao.existsById(1L)).thenReturn(false);

        RestResult<Void> result = familyMemberService.updateFamilyMember(familyMemberDTO);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("族谱树不存在", result.getMsg());
        verify(familyMemberDao, times(0)).save(any(FamilyMember.class));
    }

    @Test
    public void testDeleteFamilyMember_Success() {
        FamilyMemberQuery familyMemberQuery = new FamilyMemberQuery();
        familyMemberQuery.setMemberId(1L);

        FamilyMember familyMember = new FamilyMember();
        familyMember.setMemberId(1L);
        familyMember.setIsDeleted(0);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(familyMember));
        when(familyMemberDao.save(any(FamilyMember.class))).thenReturn(familyMember);

        RestResult<Void> result = familyMemberService.deleteFamilyMember(familyMemberQuery);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        verify(familyMemberDao, times(1)).save(any(FamilyMember.class));
    }

    @Test
    public void testDeleteFamilyMember_FamilyMemberNotFound() {
        FamilyMemberQuery familyMemberQuery = new FamilyMemberQuery();
        familyMemberQuery.setMemberId(1L);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<Void> result = familyMemberService.deleteFamilyMember(familyMemberQuery);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员信息不存在", result.getMsg());
        verify(familyMemberDao, times(0)).save(any(FamilyMember.class));
    }

    @Test
    public void testGetFamilyMemberInfo_Success() {
        FamilyMemberQuery familyMemberQuery = new FamilyMemberQuery();
        familyMemberQuery.setMemberId(1L);

        FamilyMember familyMember = new FamilyMember();
        familyMember.setMemberId(1L);
        familyMember.setName("张三");
        familyMember.setGender(1);
        familyMember.setIsDeleted(0);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.of(familyMember));

        RestResult<FamilyMemberDTO> result = familyMemberService.getFamilyMemberInfo(familyMemberQuery);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        assertEquals("张三", result.getData().getName());
        assertEquals(1, result.getData().getGender().intValue());
    }

    @Test
    public void testGetFamilyMemberInfo_FamilyMemberNotFound() {
        FamilyMemberQuery familyMemberQuery = new FamilyMemberQuery();
        familyMemberQuery.setMemberId(1L);

        when(familyMemberDao.findByMemberIdAndIsDeleted(1L, 0)).thenReturn(Optional.empty());

        RestResult<FamilyMemberDTO> result = familyMemberService.getFamilyMemberInfo(familyMemberQuery);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员信息不存在", result.getMsg());
        assertEquals(null, result.getData());
    }
}