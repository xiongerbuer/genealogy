package com.genealogy.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *   用户角色关联实体类
 * </p>
 * @author xiongyou
 */
@Entity
@Table(name = "user_role_relation")
@Data
public class UserRoleRelation {

    /**
     * 关联记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}
