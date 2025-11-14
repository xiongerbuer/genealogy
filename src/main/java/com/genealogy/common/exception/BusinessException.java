package com.genealogy.common.exception;

import lombok.Data;
import lombok.Getter;

/**
 * <p>
 *   业务异常类
 * </p>
 * @author xiongyou
 */
@Data
@Getter
public class BusinessException extends RuntimeException {

    private String code;

    private String msg;

    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
