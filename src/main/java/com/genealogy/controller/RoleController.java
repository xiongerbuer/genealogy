package com.genealogy.controller;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RoleAddDTO;
import com.genealogy.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   角色控制器
 * </p>
 * @author xiongyou
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    @ApiOperation("新增角色")
    public RestResult<Long> addRole(@RequestBody @Valid RoleAddDTO roleAddDTO) {
        Long roleId = roleService.addRole(roleAddDTO);
        return new RestResult<>(ResultCodeConstant.CODE_000000, ResultCodeConstant.CODE_000000_MSG, roleId);
    }
}