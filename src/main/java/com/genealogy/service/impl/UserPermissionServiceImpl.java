package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.entity.SysRole;
import com.genealogy.entity.SysUser;
import com.genealogy.entity.UserRoleRelation;
import com.genealogy.dao.SysRoleDao;
import com.genealogy.dao.SysUserDao;
import com.genealogy.dao.UserPermissionDao;
import com.genealogy.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *   用户权限配置服务层实现
 * </p>
 * @author xiongyou
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private UserPermissionDao userPermissionDao;

    @Override
    @Transactional
    public RestResult<Void> configureUserPermissions(UserPermissionDTO userPermissionDTO) {
        Long userId = userPermissionDTO.getUserId();
        List<Long> roleIds = userPermissionDTO.getRoleIds();

        Optional<SysUser> userOptional = sysUserDao.findById(userId);
        if (!userOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG);
        }

        List<SysRole> roles = sysRoleDao.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "角色不存在");
        }

        userPermissionDao.deleteByUserId(userId);

        List<UserRoleRelation> userRoleRelations = roleIds.stream()
                .map(roleId -> {
                    UserRoleRelation relation = new UserRoleRelation();
                    relation.setUserId(userId);
                    relation.setRoleId(roleId);
                    relation.setCreateBy(userOptional.get().getUsername());
                    relation.setCreateTime(LocalDateTime.now());
                    relation.setIsDeleted(0);
                    return relation;
                })
                .collect(Collectors.toList());

        userPermissionDao.saveAll(userRoleRelations);

        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }

    @Override
    public RestResult<Void> configureDataEncryption(UserPermissionDTO userPermissionDTO) {
        String tableName = userPermissionDTO.getTableName();
        String columnName = userPermissionDTO.getColumnName();

        // 这里需要实现表和列存在的校验逻辑
        // 为了示例简化，假设校验通过
        // 实际项目中可以通过 JDBC 查询数据库元数据进行校验

        // 配置数据加密策略
        // 这里需要实现具体的加密策略配置逻辑
        // 为了示例简化，假设配置成功

        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }

    @Override
    @Transactional
    public RestResult<Void> setDataAccessPermissions(UserPermissionDTO userPermissionDTO) {
        List<Long> roleIds = userPermissionDTO.getRoleIds();
        Long resourceId = userPermissionDTO.getResourceId();
        String permission = userPermissionDTO.getPermission();

        List<SysRole> roleList = sysRoleDao.findAllById(roleIds);
        if (CollectionUtils.isEmpty(roleList)) {
            return new RestResult<>(ResultCodeConstant.CODE_000001, "角色不存在");
        }

        // 这里需要实现资源存在的校验逻辑
        // 为了示例简化，假设校验通过
        // 实际项目中可以通过资源表的查询进行校验

        // 配置角色对资源的访问权限
        // 这里需要实现具体的权限配置逻辑
        // 为了示例简化，假设配置成功

        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }

}