package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.MediaResourceDTO;
import com.genealogy.entity.FamilyMember;
import com.genealogy.entity.MediaResource;
import com.genealogy.query.MediaResourceQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.MediaResourceDao;
import com.genealogy.service.MediaResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   多媒体资源服务层实现
 * </p>
 * @author xiongyou
 */
@Service
public class MediaResourceServiceImpl implements MediaResourceService {

    @Autowired
    private MediaResourceDao mediaResourceDao;

    @Autowired
    private FamilyMemberDao familyMemberDao;

    @Override
    @Transactional
    public RestResult<Long> uploadMediaResource(MediaResourceDTO mediaResourceDTO) {
        // 校验文件类型是否为支持的类型
        if (!isSupportedFileType(mediaResourceDTO.getFileType())) {
            return new RestResult<>(ResultCodeConstant.CODE_000002, "文件类型不支持", null);
        }

        // 检查成员是否存在
        Optional<FamilyMember> familyMemberOptional = familyMemberDao.findById(mediaResourceDTO.getMemberId());
        if (!familyMemberOptional.isPresent()) {
            return new RestResult<>(ResultCodeConstant.CODE_000003, "成员不存在", null);
        }

        // 将多媒体资料信息保存到数据库
        MediaResource mediaResource = new MediaResource();
        BeanUtils.copyProperties(mediaResourceDTO, mediaResource);
        mediaResource.setUploadTime(new Timestamp(System.currentTimeMillis()));
        mediaResource.setIsDeleted(0);
        MediaResource savedMediaResource = mediaResourceDao.save(mediaResource);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", savedMediaResource.getResourceId());
    }

    @Override
    public RestResult<List<MediaResource>> queryMediaResources(MediaResourceQuery mediaResourceQuery) {
        // 验证成员ID是否合法
        if (mediaResourceQuery.getMemberId() == null) {
            return new RestResult<>(ResultCodeConstant.CODE_000004, "成员ID无效", null);
        }

        // 根据成员ID查询关联的多媒体资料列表
        List<MediaResource> mediaResources = mediaResourceDao.findByMemberId(mediaResourceQuery.getMemberId());
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", mediaResources);
    }

    @Override
    @Transactional
    public RestResult<Void> deleteMediaResource(MediaResourceQuery mediaResourceQuery) {
        // 判断资源是否存在
        MediaResource mediaResource = mediaResourceDao.findByResourceId(mediaResourceQuery.getResourceId());
        if (mediaResource == null) {
            return new RestResult<>(ResultCodeConstant.CODE_000005, "资源不存在", null);
        }

        // 标记资源为删除状态
        mediaResource.setIsDeleted(1);
        mediaResourceDao.save(mediaResource);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", null);
    }

    @Override
    @Transactional
    public RestResult<Void> updateMediaResourceDescription(MediaResourceDTO mediaResourceDTO) {
        // 确认资源是否存在
        MediaResource mediaResource = mediaResourceDao.findByResourceId(mediaResourceDTO.getResourceId());
        if (mediaResource == null) {
            return new RestResult<>(ResultCodeConstant.CODE_000005, "资源不存在", null);
        }

        // 更新多媒体资料的描述信息
        mediaResource.setDescription(mediaResourceDTO.getDescription());
        mediaResourceDao.save(mediaResource);
        return new RestResult<>(ResultCodeConstant.CODE_000000, "调用成功", null);
    }

    /**
     * 判断文件类型是否为支持的类型
     * @param fileType 文件类型
     * @return 是否支持
     */
    private boolean isSupportedFileType(String fileType) {
        return "image".equalsIgnoreCase(fileType) || "audio".equalsIgnoreCase(fileType) || "video".equalsIgnoreCase(fileType);
    }
}