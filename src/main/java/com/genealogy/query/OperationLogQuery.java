package com.genealogy.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 *   操作日志查询条件
 * </p>
 * @author xiongyou
 */
@Data
public class OperationLogQuery {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

}