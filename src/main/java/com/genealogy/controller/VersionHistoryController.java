package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.VersionHistoryDetailDTO;
import com.genealogy.query.VersionHistoryQuery;
import com.genealogy.service.VersionHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 版本历史控制器
 * 
 * @author xiongyou
 */
@Api("版本历史管理")
@RequestMapping("/version-history")
@RestController
@Validated
public class VersionHistoryController {

    @Autowired
    private VersionHistoryService versionHistoryService;

    /**
     * 查询版本历史列表
     * 
     * @param query 查询参数
     * @return 版本历史列表
     */
    @PostMapping("/list")
    @ApiOperation("查询版本历史列表")
    public RestResult<List<VersionHistoryDetailDTO>> listVersionHistory(@RequestBody @Valid VersionHistoryQuery query) {
        return versionHistoryService.listVersionHistory(query);
    }

    /**
     * 查看指定版本详情
     * 
     * @param query 查询参数
     * @return 版本详情
     */
    @GetMapping("/detail")
    @ApiOperation("查看指定版本详情")
    public RestResult<VersionHistoryDetailDTO> getVersionHistoryDetail(@Valid VersionHistoryQuery query) {
        return versionHistoryService.getVersionHistoryDetail(query);
    }

    /**
     * 回滚至指定版本
     * 
     * @param query 查询参数
     * @return 回滚结果
     */
    @PostMapping("/rollback")
    @ApiOperation("回滚至指定版本")
    public RestResult<String> rollbackToVersion(@RequestBody @Valid VersionHistoryQuery query) {
        return versionHistoryService.rollbackToVersion(query);
    }
}