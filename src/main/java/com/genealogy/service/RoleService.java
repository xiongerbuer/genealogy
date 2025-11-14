package com.genealogy.service;

import com.genealogy.dto.RoleAddDTO;

/**
 * <p>
 *   角色服务接口
 * </p>
 * @author xiongyou
 */
public interface RoleService {
    /**
     * 新增角色
     * @param roleAddDTO 角色添加参数
     * @return 角色ID
     */
    Long addRole(RoleAddDTO roleAddDTO);
}