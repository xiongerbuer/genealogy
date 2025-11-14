package com.genealogy.dao;

import com.genealogy.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>
 *   用户数据访问层
 * </p>
 * @author xiongyou
 */
@Repository
public interface SysUserDao extends JpaRepository<SysUser, Long> {

    Optional<SysUser> findByUsernameAndIsDeleted(String username, Integer isDeleted);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体类
     */
    Optional<SysUser> findByUsername(String username);
}
