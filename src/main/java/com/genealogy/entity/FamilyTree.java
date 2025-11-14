package com.genealogy.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 *   族谱树实体类
 * </p>
 * @author xiongyou
 */
@TableName("family_tree")
@Entity
@Data
public class FamilyTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long treeId;

    @Column(name = "tree_name")
    private String treeName;

    @Column(name = "description")
    private String description;

    @Column(name = "root_member_id")
    private Long rootMemberId;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "is_deleted")
    private Integer isDeleted;
}
