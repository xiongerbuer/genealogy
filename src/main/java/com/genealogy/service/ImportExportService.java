package com.genealogy.service;

import com.genealogy.dto.ExportDataDTO;
import com.genealogy.dto.ImportDataDTO;

/**
 * 导入导出服务接口
 * 
 * @author xiongyou
 */
public interface ImportExportService {

    /**
     * 导入族谱数据
     *
     * @param importDataDTO 导入数据DTO
     * @throws Exception 异常
     */
    void importFamilyData(ImportDataDTO importDataDTO) throws Exception;

    /**
     * 导出族谱数据
     *
     * @param exportDataDTO 导出数据DTO
     * @throws Exception 异常
     */
    void exportFamilyData(ExportDataDTO exportDataDTO) throws Exception;
}