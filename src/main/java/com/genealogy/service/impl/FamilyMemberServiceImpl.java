package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.FamilyMemberDTO;
import com.genealogy.entity.FamilyMember;
import com.genealogy.query.FamilyMemberQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.FamilyTreeDao;
import com.genealogy.service.FamilyMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    @Autowired
    private FamilyMemberDao familyMemberDao;

    @Autowired
    private FamilyTreeDao familyTreeDao;

    @Override
    @Transactional
    public RestResult<Long> addFamilyMember(FamilyMemberDTO familyMemberDTO) {
        if (!familyTreeDao.existsById(familyMemberDTO.getTreeId())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "族谱树不存在", null);
        }
        if (familyMemberDTO.getName() == null || familyMemberDTO.getName().isEmpty()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "成员姓名不能为空", null);
        }
        FamilyMember familyMember = new FamilyMember();
        BeanUtils.copyProperties(familyMemberDTO, familyMember);
        familyMember.setIsDeleted(0);
        familyMember.setCreateTime(LocalDateTime.now());
        familyMember.setUpdateTime(LocalDateTime.now());
        FamilyMember savedFamilyMember = familyMemberDao.save(familyMember);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", savedFamilyMember.getMemberId());
    }

    @Override
    @Transactional
    public RestResult<Void> updateFamilyMember(FamilyMemberDTO familyMemberDTO) {
        Optional<FamilyMember> familyMemberOptional = familyMemberDao.findByMemberIdAndIsDeleted(familyMemberDTO.getMemberId(), 0);
        if (!familyMemberOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "成员信息不存在", null);
        }
        FamilyMember familyMember = familyMemberOptional.get();
        if (familyMemberDTO.getTreeId() != null && !familyTreeDao.existsById(familyMemberDTO.getTreeId())) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "族谱树不存在", null);
        }
        BeanUtils.copyProperties(familyMemberDTO, familyMember, "memberId", "createTime", "isDeleted");
        familyMember.setUpdateTime(LocalDateTime.now());
        familyMemberDao.save(familyMember);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", null);
    }

    @Override
    @Transactional
    public RestResult<Void> deleteFamilyMember(FamilyMemberQuery familyMemberQuery) {
        Optional<FamilyMember> familyMemberOptional = familyMemberDao.findByMemberIdAndIsDeleted(familyMemberQuery.getMemberId(), 0);
        if (!familyMemberOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "成员信息不存在", null);
        }
        FamilyMember familyMember = familyMemberOptional.get();
        familyMember.setIsDeleted(1);
        familyMember.setUpdateTime(LocalDateTime.now());
        familyMemberDao.save(familyMember);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", null);
    }

    @Override
    public RestResult<FamilyMemberDTO> getFamilyMemberInfo(FamilyMemberQuery familyMemberQuery) {
        Optional<FamilyMember> familyMemberOptional = familyMemberDao.findByMemberIdAndIsDeleted(familyMemberQuery.getMemberId(), 0);
        if (!familyMemberOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "成员信息不存在", null);
        }
        FamilyMember familyMember = familyMemberOptional.get();
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        BeanUtils.copyProperties(familyMember, familyMemberDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", familyMemberDTO);
    }

    @Override
    public List<FamilyMemberDTO> searchByName(FamilyMemberQuery query) {
        // 校验参数
        if (query.getName() == null || query.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("成员姓名不能为空");
        }
        // 根据姓名和族谱ID查询符合条件的成员信息
        List<FamilyMember> members = familyMemberDao.findByNameAndTreeId(query.getName(), query.getTreeId());
        return convertToDTOList(members);
    }

    @Override
    public List<FamilyMemberDTO> searchByRelation(FamilyMemberQuery query) {
        // 校验参数
        if (query.getRelationType() == null || query.getRelationType().trim().isEmpty()) {
            throw new IllegalArgumentException("关系类型不能为空");
        }
        // 根据关系类型和族谱ID查询相关成员信息
        List<FamilyMember> members = familyMemberDao.findByRelationTypeAndTreeId(query.getRelationType(), query.getTreeId());
        return convertToDTOList(members);
    }

    @Override
    public List<FamilyMemberDTO> searchByYearRange(FamilyMemberQuery query) {
        // 校验参数
        if (query.getStartDate() == null || query.getEndDate() == null) {
            throw new IllegalArgumentException("开始年份和结束年份不能为空");
        }
        if (query.getStartDate().isAfter(query.getEndDate())) {
            throw new IllegalArgumentException("开始年份不能晚于结束年份");
        }
        // 根据出生年份范围和族谱ID筛选成员信息
        List<FamilyMember> members = familyMemberDao.findByBirthDateBetweenAndTreeId(query.getStartDate(), query.getEndDate(), query.getTreeId());
        return convertToDTOList(members);
    }

    @Override
    public List<FamilyMemberDTO> searchByMultipleConditions(FamilyMemberQuery query) {
        // 构建动态查询条件
        List<FamilyMember> members = familyMemberDao.searchByMultipleConditions(query.getName(), query.getRelationType(), query.getStartDate(), query.getEndDate(), query.getTreeId());
        return convertToDTOList(members);
    }

    /**
     * 将实体类列表转换为DTO列表
     * @param members 实体类列表
     * @return DTO列表
     */
    private List<FamilyMemberDTO> convertToDTOList(List<FamilyMember> members) {
        List<FamilyMemberDTO> dtos = new ArrayList<>();
        for (FamilyMember member : members) {
            FamilyMemberDTO dto = new FamilyMemberDTO();
            dto.setMemberId(member.getMemberId());
            dto.setName(member.getName());
            dto.setGender(member.getGender());
            dto.setBirthDate(member.getBirthDate());
            dto.setDeathDate(member.getDeathDate());
            dto.setPhone(member.getPhone());
            dto.setEmail(member.getEmail());
            dto.setAddress(member.getAddress());
            dto.setRemark(member.getRemark());
            dto.setTreeId(member.getTreeId());
            dtos.add(dto);
        }
        return dtos;
    }
}
