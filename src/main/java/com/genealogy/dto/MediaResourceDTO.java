package com.genealogy.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * <p>
 *   多媒体资源数据传输对象
 * </p>
 * @author xiongyou
 */
@Data
public class MediaResourceDTO {

    /**
     * 资源ID
     */
    @NotNull(message = "资源ID不能为空")
    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

    /**
     * 关联成员ID
     */
    @NotNull(message = "关联成员ID不能为空")
    @ApiModelProperty(value = "关联成员ID")
    private Long memberId;

    /**
     * 文件名称
     */
    @NotNull(message = "文件名称不能为空")
    @Size(max = 200, message = "文件名称长度不能超过200")
    @ApiModelProperty(value = "文件名称")
    private String fileName;

    /**
     * 文件路径
     */
    @NotNull(message = "文件路径不能为空")
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    /**
     * 文件类型（image/audio/video）
     */
    @NotNull(message = "文件类型不能为空")
    @Size(max = 20, message = "文件类型长度不能超过20")
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    /**
     * 文件描述
     */
    @NotNull(message = "文件描述不能为空")
    @ApiModelProperty(value = "文件描述")
    private String description;
}