package com.genealogy.service;

import com.genealogy.dto.*;
import com.genealogy.dto.RoleAddDTO;
import com.genealogy.dto.UserLoginDTO;
import com.genealogy.dto.UserRegisterDTO;
import com.genealogy.dto.UserRoleAssignDTO;
import com.genealogy.dto.UserStatusUpdateDTO;

/**
 * <p>
 *   用户服务接口
 * </p>
 * @author xiongyou
 */
public interface UserService {
    /**
     * 用户注册
     * @param userRegisterDTO 用户注册参数
     * @return 用户ID
     */
    Long register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 用户登录参数
     * @return 登录令牌
     */
    String login(UserLoginDTO userLoginDTO);

    /**
     * 新增角色
     * @param roleAddDTO 角色添加参数
     * @return 角色ID
     */
    Long addRole(RoleAddDTO roleAddDTO);

    /**
     * 分配用户角色
     * @param userRoleAssignDTO 用户角色分配参数
     */
    void assignUserRole(UserRoleAssignDTO userRoleAssignDTO);

    /**
     * 获取用户权限列表
     * @param userId 用户ID
     * @return 权限数组
     */
    String[] getUserPermissions(Long userId);

    /**
     * 修改用户状态
     * @param userStatusUpdateDTO 用户状态更新参数
     */
    void updateUserStatus(UserStatusUpdateDTO userStatusUpdateDTO);
}