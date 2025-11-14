package com.genealogy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@TableName("family_member")
@Data
public class FamilyMember {

    /**
     * 成员ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 所属族谱树ID
     */
    @Column(name = "tree_id")
    private Long treeId;

    /**
     * 成员姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 辈分ID
     */
    @Column(name = "seniority_id")
    private Long seniorityId;

    /**
     * 性别：1-男，2-女，0-未知
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * 死亡日期
     */
    @Column(name = "death_date")
    private LocalDate deathDate;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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
