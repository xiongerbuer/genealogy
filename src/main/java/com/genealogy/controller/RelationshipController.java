package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RelationshipDTO;
import com.genealogy.query.RelationshipQuery;
import com.genealogy.service.RelationshipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   亲属关系控制器
 * </p>
 * @author xiongyou
 */
@Api("亲属关系管理")
@RequestMapping("relationship")
@RestController
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    /**
     * 新增亲属关系
     *
     * @param relationshipDTO 亲属关系信息
     * @return 处理结果
     */
    @PostMapping("/add")
    @ApiOperation("新增亲属关系")
    public RestResult<Long> addRelationship(@RequestBody @Validated RelationshipDTO relationshipDTO) {
        return relationshipService.addRelationship(relationshipDTO);
    }

    /**
     * 删除亲属关系
     *
     * @param relationshipQuery 亲属关系查询条件
     * @return 处理结果
     */
    @PostMapping("/delete")
    @ApiOperation("删除亲属关系")
    public RestResult<Void> deleteRelationship(@RequestBody @Validated RelationshipQuery relationshipQuery) {
        return relationshipService.deleteRelationship(relationshipQuery);
    }

    /**
     * 修改亲属关系
     *
     * @param relationshipDTO 亲属关系信息
     * @return 处理结果
     */
    @PostMapping("/update")
    @ApiOperation("修改亲属关系")
    public RestResult<Void> updateRelationship(@RequestBody @Validated RelationshipDTO relationshipDTO) {
        return relationshipService.updateRelationship(relationshipDTO);
    }

    /**
     * 查询亲属关系列表
     *
     * @param relationshipQuery 亲属关系查询条件
     * @return 亲属关系列表
     */
    @GetMapping("/list")
    @ApiOperation("查询亲属关系列表")
    public RestResult<List<RelationshipDTO>> listRelationships(@Validated RelationshipQuery relationshipQuery) {
        return relationshipService.listRelationships(relationshipQuery);
    }

    /**
     * 查询单个亲属关系详情
     *
     * @param relationshipQuery 亲属关系查询条件
     * @return 亲属关系详情
     */
    @GetMapping("/info")
    @ApiOperation("查询单个亲属关系详情")
    public RestResult<RelationshipDTO> getRelationship(@Validated RelationshipQuery relationshipQuery) {
        return relationshipService.getRelationship(relationshipQuery);
    }
}