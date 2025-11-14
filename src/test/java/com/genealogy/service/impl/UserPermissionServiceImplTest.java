package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.UserPermissionDTO;
import com.genealogy.entity.SysRole;
import com.genealogy.entity.SysUser;
import com.genealogy.dao.SysRoleDao;
import com.genealogy.dao.SysUserDao;
import com.genealogy.dao.UserPermissionDao;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * <p>
 *   用户权限配置服务层单元测试
 * </p>
 * @author xiongyou
 */
public class UserPermissionServiceImplTest {

    @Mock
    private SysUserDao sysUserDao;

    @Mock
    private SysRoleDao sysRoleDao;

    @Mock
    private UserPermissionDao userPermissionDao;

    @InjectMocks
    private UserPermissionServiceImpl userPermissionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConfigureUserPermissions_UserNotFound() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setUserId(1L);
        dto.setRoleIds(Arrays.asList(1L, 2L));

        when(sysUserDao.findById(1L)).thenReturn(Optional.empty());

        RestResult<Void> result = userPermissionService.configureUserPermissions(dto);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000001_MSG, result.getMsg());
    }

    @Test
    public void testConfigureUserPermissions_RoleNotFound() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setUserId(1L);
        dto.setRoleIds(Arrays.asList(1L, 2L));

        SysUser user = new SysUser();
        user.setUserId(1L);
        user.setUsername("testUser");

        when(sysUserDao.findById(1L)).thenReturn(Optional.of(user));
        when(sysRoleDao.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(new SysRole()));

        RestResult<Void> result = userPermissionService.configureUserPermissions(dto);

        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("角色不存在", result.getMsg());
    }

    @Test
    public void testConfigureUserPermissions_Success() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setUserId(1L);
        dto.setRoleIds(Arrays.asList(1L, 2L));

        SysUser user = new SysUser();
        user.setUserId(1L);
        user.setUsername("testUser");

        SysRole role1 = new SysRole();
        role1.setRoleId(1L);
        role1.setRoleName("role1");

        SysRole role2 = new SysRole();
        role2.setRoleId(2L);
        role2.setRoleName("role2");

        when(sysUserDao.findById(1L)).thenReturn(Optional.of(user));
        when(sysRoleDao.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(role1, role2));

        RestResult<Void> result = userPermissionService.configureUserPermissions(dto);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());

        verify(userPermissionDao, times(1)).deleteByUserId(1L);
        verify(userPermissionDao, times(1)).saveAll(anyList());
    }

    @Test
    public void testConfigureDataEncryption_TableOrColumnNotFound() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setTableName("testTable");
        dto.setColumnName("testColumn");

        // 假设表和列不存在
        // 实际项目中可以通过 JDBC 查询数据库元数据进行校验

        RestResult<Void> result = userPermissionService.configureDataEncryption(dto);

        assertEquals(ResultCodeConstant.CODE_000003, result.getCode());
        assertEquals("表或列不存在", result.getMsg());
    }

    @Test
    public void testConfigureDataEncryption_Success() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setTableName("testTable");
        dto.setColumnName("testColumn");
        dto.setEncryptType("AES");

        // 假设表和列存在
        // 实际项目中可以通过 JDBC 查询数据库元数据进行校验

        RestResult<Void> result = userPermissionService.configureDataEncryption(dto);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
    }

    @Test
    public void testSetDataAccessPermissions_RoleNotFound() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setRoleId(1L);
        dto.setResourceId(1L);
        dto.setPermission("read");

        when(sysRoleDao.findById(1L)).thenReturn(Optional.empty());

        RestResult<Void> result = userPermissionService.setDataAccessPermissions(dto);

        assertEquals(ResultCodeConstant.CODE_000002, result.getCode());
        assertEquals("角色不存在", result.getMsg());
    }

    @Test
    public void testSetDataAccessPermissions_Success() {
        UserPermissionDTO dto = new UserPermissionDTO();
        dto.setRoleId(1L);
        dto.setResourceId(1L);
        dto.setPermission("read");

        SysRole role = new SysRole();
        role.setRoleId(1L);
        role.setRoleName("role1");

        when(sysRoleDao.findById(1L)).thenReturn(Optional.of(role));

        RestResult<Void> result = userPermissionService.setDataAccessPermissions(dto);

        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals(ResultCodeConstant.CODE_000000_MSG, result.getMsg());
    }

}