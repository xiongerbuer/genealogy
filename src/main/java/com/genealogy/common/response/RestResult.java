package com.genealogy.common.response;

import com.genealogy.common.constant.ResultCodeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *   统一响应类
 * </p>
 * @author xiongyou
 */
@ApiModel(description = "统一响应结果")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestResult<T> {

    /**
     * 业务返回码
     */
    @ApiModelProperty(value = "业务返回码")
    private String code;

    /**
     * 业务提示信息
     */
    @ApiModelProperty(value = "业务提示信息")
    private String msg;

    private static final long serialVersionUID = 1L;

    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    public RestResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<T>(ResultCodeConstant.SUCCESS, ResultCodeConstant.SUCCESS, data);
    }

    public static <T> RestResult<T> success(String msg, T data) {
        return new RestResult<T>(ResultCodeConstant.SUCCESS, msg, data);
    }

    public static <T> RestResult<T> fail(String msg) {
        return new RestResult<T>(ResultCodeConstant.FAIL, msg);
    }

    public static <T> RestResult<T> fail(String code, String msg) {
        return new RestResult<T>(code, msg);
    }

}
