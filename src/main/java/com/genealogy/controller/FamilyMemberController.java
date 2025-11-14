package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.FamilyMemberDTO;
import com.genealogy.query.FamilyMemberQuery;
import com.genealogy.service.FamilyMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api("家族成员搜索浏览管理")
@RequestMapping("/family-member")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    /**
     * 新增家族成员
     *
     * @param familyMemberDTO 成员信息
     * @return RestResult 结果
     */
    @PostMapping("/add")
    @ApiOperation("新增家族成员")
    public RestResult<Long> addFamilyMember(@RequestBody @Validated(FamilyMemberDTO.CreateGroup.class) FamilyMemberDTO familyMemberDTO) {
        return familyMemberService.addFamilyMember(familyMemberDTO);
    }

    /**
     * 修改家族成员
     *
     * @param familyMemberDTO 成员信息
     * @return RestResult 结果
     */
    @PutMapping("/update")
    @ApiOperation("修改家族成员")
    public RestResult<Void> updateFamilyMember(@RequestBody @Validated(FamilyMemberDTO.UpdateGroup.class) FamilyMemberDTO familyMemberDTO) {
        return familyMemberService.updateFamilyMember(familyMemberDTO);
    }

    /**
     * 删除家族成员
     *
     * @param familyMemberQuery 成员查询条件
     * @return RestResult 结果
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除家族成员")
    public RestResult<Void> deleteFamilyMember(@RequestBody @Validated FamilyMemberQuery familyMemberQuery) {
        return familyMemberService.deleteFamilyMember(familyMemberQuery);
    }

    /**
     * 查询家族成员详情
     *
     * @param familyMemberQuery 成员查询条件
     * @return RestResult 结果
     */
    @GetMapping("/info")
    @ApiOperation("查询家族成员详情")
    public RestResult<FamilyMemberDTO> getFamilyMemberInfo(@Validated @ModelAttribute FamilyMemberQuery familyMemberQuery) {
        return familyMemberService.getFamilyMemberInfo(familyMemberQuery);
    }

    /**
     * 按姓名搜索家族成员
     *
     * @param query 查询条件
     * @return 搜索结果
     */
    @PostMapping("/search-by-name")
    @ApiOperation("按姓名搜索家族成员")
    public RestResult<List<FamilyMemberDTO>> searchByName(@Valid @RequestBody FamilyMemberQuery query) {
        List<FamilyMemberDTO> result = familyMemberService.searchByName(query);
        return new RestResult<>("000000", "调用成功", result);
    }

    /**
     * 按关系搜索家族成员
     *
     * @param query 查询条件
     * @return 搜索结果
     */
    @PostMapping("/search-by-relation")
    @ApiOperation("按关系搜索家族成员")
    public RestResult<List<FamilyMemberDTO>> searchByRelation(@Valid @RequestBody FamilyMemberQuery query) {
        List<FamilyMemberDTO> result = familyMemberService.searchByRelation(query);
        return new RestResult<>("000000", "调用成功", result);
    }

    /**
     * 按年代搜索家族成员
     *
     * @param query 查询条件
     * @return 搜索结果
     */
    @PostMapping("/search-by-year-range")
    @ApiOperation("按年代搜索家族成员")
    public RestResult<List<FamilyMemberDTO>> searchByYearRange(@Valid @RequestBody FamilyMemberQuery query) {
        List<FamilyMemberDTO> result = familyMemberService.searchByYearRange(query);
        return new RestResult<>("000000", "调用成功", result);
    }

    /**
     * 综合条件搜索家族成员
     *
     * @param query 查询条件
     * @return 搜索结果
     */
    @PostMapping("/search-by-multiple")
    @ApiOperation("综合条件搜索家族成员")
    public RestResult<List<FamilyMemberDTO>> searchByMultipleConditions(@Valid @RequestBody FamilyMemberQuery query) {
        List<FamilyMemberDTO> result = familyMemberService.searchByMultipleConditions(query);
        return new RestResult<>("000000", "调用成功", result);
    }
}
