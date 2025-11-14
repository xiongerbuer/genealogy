package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.exception.BusinessException;
import com.genealogy.dto.RoleAddDTO;
import com.genealogy.entity.SysRole;
import com.genealogy.dao.SysRoleDao;
import com.genealogy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *   角色服务实现类
 * </p>
 * @author xiongyou
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public Long addRole(RoleAddDTO roleAddDTO) {
        // 检查角色名称是否已存在
        if (sysRoleDao.findByRoleNameAndIsDeleted(roleAddDTO.getRoleName(), 0).isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000003, ResultCodeConstant.CODE_000003_MSG);
        }

        // 构建角色实体
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleAddDTO.getRoleName());
        sysRole.setDescription(roleAddDTO.getDescription());
        sysRole.setIsDeleted(0);
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setUpdateTime(LocalDateTime.now());

        // 保存角色信息
        SysRole savedRole = sysRoleDao.save(sysRole);
        return savedRole.getRoleId();
    }
}