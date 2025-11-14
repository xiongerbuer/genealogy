package com.genealogy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@TableName("statistics_report")
public class StatisticsReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "generate_time")
    private LocalDateTime generateTime;

    @Column(name = "generated_by")
    private String generatedBy;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public void setContent(JsonNode content) {
        // 将 JsonNode 转换为字符串存储
        if (content != null) {
            this.content = content.toString();
        } else {
            this.content = null;
        }
    }
}