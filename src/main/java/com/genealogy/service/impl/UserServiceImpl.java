package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.exception.BusinessException;
import com.genealogy.common.util.PasswordUtil;
import com.genealogy.dto.*;
import com.genealogy.entity.SysRole;
import com.genealogy.entity.SysUser;
import com.genealogy.entity.UserRoleRelation;
import com.genealogy.dao.SysRoleDao;
import com.genealogy.dao.SysUserDao;
import com.genealogy.dao.UserRoleRelationDao;
import com.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   用户服务实现类
 * </p>
 * @author xiongyou
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private UserRoleRelationDao userRoleRelationDao;

    @Override
    public Long register(UserRegisterDTO userRegisterDTO) {
        // 检查用户名是否已存在
        if (sysUserDao.findByUsernameAndIsDeleted(userRegisterDTO.getUsername(), 0).isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001, ResultCodeConstant.CODE_000001_MSG);
        }

        // 构建用户实体
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userRegisterDTO.getUsername());
        sysUser.setPassword(PasswordUtil.encode(userRegisterDTO.getPassword()));
        sysUser.setEmail(userRegisterDTO.getEmail());
        sysUser.setPhone(userRegisterDTO.getPhone());
        sysUser.setStatus(1); // 默认启用
        sysUser.setIsDeleted(0);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());

        // 保存用户信息
        SysUser savedUser = sysUserDao.save(sysUser);
        return savedUser.getUserId();
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        // 查询用户
        Optional<SysUser> userOpt = sysUserDao.findByUsernameAndIsDeleted(userLoginDTO.getUsername(), 0);
        if (!userOpt.isPresent()) {
            throw new BusinessException(ResultCodeConstant.CODE_000002, ResultCodeConstant.CODE_000002_MSG);
        }

        SysUser sysUser = userOpt.get();
        if (!PasswordUtil.matches(userLoginDTO.getPassword(), sysUser.getPassword())) {
            throw new BusinessException(ResultCodeConstant.CODE_000002, ResultCodeConstant.CODE_000002_MSG);
        }

        // 这里可以生成JWT Token或其他方式的令牌
        return "mock_token_" + sysUser.getUserId();
    }

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

    @Override
    public void assignUserRole(UserRoleAssignDTO userRoleAssignDTO) {
        // 检查用户是否存在
        if (!sysUserDao.existsById(userRoleAssignDTO.getUserId())) {
            throw new BusinessException(ResultCodeConstant.CODE_000004, ResultCodeConstant.CODE_000004_MSG);
        }

        // 检查角色是否存在
        if (!sysRoleDao.existsById(userRoleAssignDTO.getRoleId())) {
            throw new BusinessException(ResultCodeConstant.CODE_000005, ResultCodeConstant.CODE_000005_MSG);
        }

        // 保存用户角色关联信息
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setUserId(userRoleAssignDTO.getUserId());
        userRoleRelation.setRoleId(userRoleAssignDTO.getRoleId());
        userRoleRelation.setIsDeleted(0);
        userRoleRelation.setCreateTime(LocalDateTime.now());

        userRoleRelationDao.save(userRoleRelation);
    }

    @Override
    public String[] getUserPermissions(Long userId) {
        // 检查用户是否存在
        if (!sysUserDao.existsById(userId)) {
            throw new BusinessException(ResultCodeConstant.CODE_000004, ResultCodeConstant.CODE_000004_MSG);
        }

        // 获取用户角色列表
        List<String> permissions = new ArrayList<>();
        // 示例：假设用户拥有多个角色，每个角色对应不同的权限
        // 实际项目中应从数据库查询具体权限信息
        permissions.add("read:user");
        permissions.add("write:user");

        return permissions.toArray(new String[0]);
    }

    @Override
    public void updateUserStatus(UserStatusUpdateDTO userStatusUpdateDTO) {
        // 检查用户是否存在
        if (!sysUserDao.existsById(userStatusUpdateDTO.getUserId())) {
            throw new BusinessException(ResultCodeConstant.CODE_000004, ResultCodeConstant.CODE_000004_MSG);
        }

        // 更新用户状态
        SysUser sysUser = sysUserDao.findById(userStatusUpdateDTO.getUserId()).orElse(null);
        if (sysUser != null) {
            sysUser.setStatus(userStatusUpdateDTO.getStatus());
            sysUser.setUpdateTime(LocalDateTime.now());
            sysUserDao.save(sysUser);
        }
    }
}