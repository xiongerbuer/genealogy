package com.genealogy.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * <p>
 *   亲属关系实体类
 * </p>
 * @author xiongyou
 */
@Entity
@Table(name = "relationship")
@Data
public class Relationship {

    /**
     * 关系ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long relationId;

    /**
     * 起始成员ID
     */
    @Column(name = "from_member_id")
    private Long fromMemberId;

    /**
     * 目标成员ID
     */
    @Column(name = "to_member_id")
    private Long toMemberId;

    /**
     * 关系类型（如父子、夫妻等）
     */
    @Column(name = "relation_type")
    private String relationType;

    /**
     * 备注说明
     */
    @Column(name = "note")
    private String note;

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
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}
