package com.genealogy.entity;

import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Data;

/**
 * <p>
 *   多媒体资源实体类
 * </p>
 * @author xiongyou
 */
@Data
@Entity
@Table(name = "media_resource")
public class MediaResource {

    /**
     * 资源ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

    /**
     * 关联成员ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件类型（image/audio/video）
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件大小（字节）
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Timestamp uploadTime;

    /**
     * 上传人
     */
    @Column(name = "upload_by")
    private String uploadBy;

    /**
     * 文件描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}