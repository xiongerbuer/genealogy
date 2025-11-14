package com.genealogy.controller;

import com.genealogy.common.exception.BusinessException;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.ExportDataDTO;
import com.genealogy.dto.ImportDataDTO;
import com.genealogy.service.ImportExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 导入导出控制器
 * 
 * @author xiongyou
 */
@Api("导入导出管理")
@RestController
@RequestMapping("/import-export")
public class ImportExportController {

    @Autowired
    private ImportExportService importExportService;

    /**
     * 导入族谱数据
     *
     * @param importDataDTO 导入数据DTO
     * @return RestResult
     */
    @PostMapping("/import")
    @ApiOperation("导入族谱数据")
    public RestResult<String> importFamilyData(@Valid @RequestBody ImportDataDTO importDataDTO) {
        try {
            importExportService.importFamilyData(importDataDTO);
            return new RestResult<>("000000", "导入成功");
        } catch (BusinessException e) {
            return new RestResult<>(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return new RestResult<>("999999", "系统异常");
        }
    }

    /**
     * 导出族谱数据
     *
     * @param exportDataDTO 导出数据DTO
     * @return RestResult
     */
    @GetMapping("/export")
    @ApiOperation("导出族谱数据")
    public RestResult<String> exportFamilyData(@Valid ExportDataDTO exportDataDTO) {
        try {
            importExportService.exportFamilyData(exportDataDTO);
            return new RestResult<>("000000", "导出成功");
        } catch (BusinessException e) {
            return new RestResult<>(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return new RestResult<>("999999", "系统异常");
        }
    }
}