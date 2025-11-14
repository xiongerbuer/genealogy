package com.genealogy.controller;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RoleAddDTO;
import com.genealogy.dto.UserLoginDTO;
import com.genealogy.dto.UserRegisterDTO;
import com.genealogy.dto.UserRoleAssignDTO;
import com.genealogy.dto.UserStatusUpdateDTO;
import com.genealogy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *   权限认证控制器
 * </p>
 * @author xiongyou
 */
@Api(tags = "用户权限管理")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public RestResult<Long> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        Long userId = userService.register(userRegisterDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, userId);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public RestResult<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, token);
    }

    @PostMapping("/role/add")
    @ApiOperation("新增角色")
    public RestResult<Long> addRole(@RequestBody @Validated RoleAddDTO roleAddDTO) {
        Long roleId = userService.addRole(roleAddDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, roleId);
    }

    @PostMapping("/assign-role")
    @ApiOperation("分配用户角色")
    public RestResult<Void> assignUserRole(@RequestBody @Valid UserRoleAssignDTO userRoleAssignDTO) {
        userService.assignUserRole(userRoleAssignDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }

    @GetMapping("/permissions/{userId}")
    @ApiOperation("获取用户权限列表")
    public RestResult<String[]> getUserPermissions(@PathVariable Long userId) {
        String[] permissions = userService.getUserPermissions(userId);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, permissions);
    }

    @PutMapping("/status")
    @ApiOperation("修改用户状态")
    public RestResult<Void> updateUserStatus(@RequestBody @Valid UserStatusUpdateDTO userStatusUpdateDTO) {
        userService.updateUserStatus(userStatusUpdateDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG);
    }
}