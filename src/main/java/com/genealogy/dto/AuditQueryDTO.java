package com.genealogy.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 查询待审核列表数据传输对象
 * 
 * @author xiongyou
 */
@Data
public class AuditQueryDTO {

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为1")
    private Integer pageIndex;

    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为1")
    private Integer pageSize;
}