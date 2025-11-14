package com.genealogy.dao;

import com.genealogy.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   角色数据访问层
 * </p>
 * @author xiongyou
 */
@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Long> {

    Optional<SysRole> findByRoleNameAndIsDeleted(String roleName, Integer isDeleted);

    /**
     * 根据角色ID查找角色
     * @param roleIds 角色ID列表
     * @return 角色实体类列表
     */
    @Query("SELECT r FROM SysRole r WHERE r.roleId IN :roleIds")
    List<SysRole> findAllById(@Param("ids") List<Long> roleIds);
}
