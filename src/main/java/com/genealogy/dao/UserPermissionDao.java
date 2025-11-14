package com.genealogy.dao;

import com.genealogy.entity.UserRoleRelation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *   用户权限配置数据访问层
 * </p>
 * @author xiongyou
 */
public interface UserPermissionDao extends JpaRepository<UserRoleRelation, Long> {

    /**
     * 根据用户ID删除所有角色关联关系
     * @param userId 用户ID
     */
    @Modifying
    @Query("DELETE FROM UserRoleRelation ur WHERE ur.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查找所有角色关联关系
     * @param userId 用户ID
     * @return 角色关联关系列表
     */
    List<UserRoleRelation> findByUserId(Long userId);

}