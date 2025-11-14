package com.genealogy.controller;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.FamilyTreeDTO;
import com.genealogy.entity.FamilyTree;
import com.genealogy.query.FamilyTreeQuery;
import com.genealogy.service.FamilyTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *   族谱树控制器
 * </p>
 * @author xiongyou
 */
@Api("族谱结构管理")
@RequestMapping("family-tree")
@RestController
public class FamilyTreeController {

    @Autowired
    private FamilyTreeService familyTreeService;

    /**
     * 新建族谱树
     *
     * @param familyTreeDTO 族谱树信息
     * @return RestResult
     */
    @PostMapping("/create")
    @ApiOperation("新建族谱树")
    public RestResult<Long> createFamilyTree(@RequestBody @Validated FamilyTreeDTO familyTreeDTO) {
        Long treeId = familyTreeService.createFamilyTree(familyTreeDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", treeId);
    }

    /**
     * 编辑族谱树
     *
     * @param familyTreeDTO 族谱树信息
     * @return RestResult
     */
    @PutMapping("/update")
    @ApiOperation("编辑族谱树")
    public RestResult<Boolean> updateFamilyTree(@RequestBody @Validated FamilyTreeDTO familyTreeDTO) {
        boolean success = familyTreeService.updateFamilyTree(familyTreeDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", success);
    }

    /**
     * 删除族谱树
     *
     * @param familyTreeQuery 族谱树查询条件
     * @return RestResult
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除族谱树")
    public RestResult<Boolean> deleteFamilyTree(@RequestBody @Validated FamilyTreeQuery familyTreeQuery) {
        boolean success = familyTreeService.deleteFamilyTree(familyTreeQuery);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", success);
    }

    /**
     * 查询族谱树详情
     *
     * @param familyTreeQuery 族谱树查询条件
     * @return RestResult
     */
    @GetMapping("/details")
    @ApiOperation("查询族谱树详情")
    public RestResult<FamilyTree> getFamilyTreeDetails(@RequestBody @Validated FamilyTreeQuery familyTreeQuery) {
        FamilyTree familyTree = familyTreeService.getFamilyTreeDetails(familyTreeQuery);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", familyTree);
    }

    /**
     * 查询族谱树列表
     *
     * @param treeName 族谱名称
     * @return RestResult<List<FamilyTree>>
     */
    @GetMapping("/list")
    @ApiOperation("查询族谱树列表")
    public RestResult<List<FamilyTree>> getFamilyTreeList(@RequestParam(required = false) String treeName) {
        List<FamilyTree> familyTreeList = familyTreeService.getFamilyTreeList(treeName);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", familyTreeList);
    }
}