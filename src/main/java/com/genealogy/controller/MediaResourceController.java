package com.genealogy.controller;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.MediaResourceDTO;
import com.genealogy.entity.MediaResource;
import com.genealogy.query.MediaResourceQuery;
import com.genealogy.service.MediaResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   多媒体资源管理控制器
 * </p>
 * @author xiongyou
 */
@Api("多媒体资料管理")
@RequestMapping("media")
@RestController
public class MediaResourceController {

    @Autowired
    private MediaResourceService mediaResourceService;

    /**
     * 上传多媒体资料
     *
     * @param mediaResourceDTO 多媒体资料信息
     * @return RestResult结果
     */
    @PostMapping("/upload")
    @ApiOperation("上传多媒体资料")
    public RestResult<Long> uploadMediaResource(@Valid @RequestBody MediaResourceDTO mediaResourceDTO) {
        return mediaResourceService.uploadMediaResource(mediaResourceDTO);
    }

    /**
     * 查询成员多媒体资料
     *
     * @param mediaResourceQuery 查询条件
     * @return RestResult结果
     */
    @GetMapping("/query")
    @ApiOperation("查询成员多媒体资料")
    public RestResult<List<MediaResource>> queryMediaResources(@Valid MediaResourceQuery mediaResourceQuery) {
        return mediaResourceService.queryMediaResources(mediaResourceQuery);
    }

    /**
     * 删除多媒体资料
     *
     * @param mediaResourceQuery 查询条件
     * @return RestResult结果
     */
    @PostMapping("/delete")
    @ApiOperation("删除多媒体资料")
    public RestResult<Void> deleteMediaResource(@Valid @RequestBody MediaResourceQuery mediaResourceQuery) {
        return mediaResourceService.deleteMediaResource(mediaResourceQuery);
    }

    /**
     * 更新多媒体资料描述
     *
     * @param mediaResourceDTO 多媒体资料信息
     * @return RestResult结果
     */
    @PostMapping("/updateDescription")
    @ApiOperation("更新多媒体资料描述")
    public RestResult<Void> updateMediaResourceDescription(@Valid @RequestBody MediaResourceDTO mediaResourceDTO) {
        return mediaResourceService.updateMediaResourceDescription(mediaResourceDTO);
    }
}