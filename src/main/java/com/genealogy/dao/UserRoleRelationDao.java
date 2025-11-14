package com.genealogy.dao;

import com.genealogy.entity.UserRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *   用户角色关联数据访问层
 * </p>
 * @author xiongyou
 */
@Repository
public interface UserRoleRelationDao extends JpaRepository<UserRoleRelation, Long> {
}