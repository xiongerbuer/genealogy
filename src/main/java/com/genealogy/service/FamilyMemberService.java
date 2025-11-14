package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.FamilyMemberDTO;
import com.genealogy.query.FamilyMemberQuery;
import java.util.List;

public interface FamilyMemberService {

    /**
     * 新增家族成员
     * @param familyMemberDTO 成员信息
     * @return RestResult 结果
     */
    RestResult<Long> addFamilyMember(FamilyMemberDTO familyMemberDTO);

    /**
     * 修改家族成员
     * @param familyMemberDTO 成员信息
     * @return RestResult 结果
     */
    RestResult<Void> updateFamilyMember(FamilyMemberDTO familyMemberDTO);

    /**
     * 删除家族成员
     * @param familyMemberQuery 成员查询条件
     * @return RestResult 结果
     */
    RestResult<Void> deleteFamilyMember(FamilyMemberQuery familyMemberQuery);

    /**
     * 查询家族成员详情
     * @param familyMemberQuery 成员查询条件
     * @return RestResult 结果
     */
    RestResult<FamilyMemberDTO> getFamilyMemberInfo(FamilyMemberQuery familyMemberQuery);

    /**
     * 按姓名搜索家族成员
     * @param query 查询条件
     * @return 家族成员列表
     */
    List<FamilyMemberDTO> searchByName(FamilyMemberQuery query);

    /**
     * 按关系搜索家族成员
     * @param query 查询条件
     * @return 家族成员列表
     */
    List<FamilyMemberDTO> searchByRelation(FamilyMemberQuery query);

    /**
     * 按年代搜索家族成员
     * @param query 查询条件
     * @return 家族成员列表
     */
    List<FamilyMemberDTO> searchByYearRange(FamilyMemberQuery query);

    /**
     * 综合条件搜索家族成员
     * @param query 查询条件
     * @return 家族成员列表
     */
    List<FamilyMemberDTO> searchByMultipleConditions(FamilyMemberQuery query);
}
