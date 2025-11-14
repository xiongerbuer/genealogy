package com.genealogy.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * 数据导入日志实体类
 * 
 * @author xiongyou
 */
@Data
@Entity
@Table(name = "data_import_log")
public class DataImportLog {

    /**
     * 导入记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id")
    private Long importId;

    /**
     * 导入文件名
     */
    @Column(name = "import_file_name")
    private String importFileName;

    /**
     * 导入类型
     */
    @Column(name = "import_type")
    private String importType;

    /**
     * 导入状态：0-失败，1-成功
     */
    @Column(name = "import_status")
    private Integer importStatus;

    /**
     * 导入结果详情
     */
    @Column(name = "import_result")
    private String importResult;

    /**
     * 导入时间
     */
    @Column(name = "import_time")
    private LocalDateTime importTime;

    /**
     * 导入人
     */
    @Column(name = "imported_by")
    private String importedBy;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Integer isDeleted;
}