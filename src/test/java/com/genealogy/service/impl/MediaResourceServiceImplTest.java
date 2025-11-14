package com.genealogy.service.impl;

import com.genealogy.common.constant.ResultCodeConstant;
import com.genealogy.common.response.RestResult;
import com.genealogy.dto.MediaResourceDTO;
import com.genealogy.entity.FamilyMember;
import com.genealogy.entity.MediaResource;
import com.genealogy.query.MediaResourceQuery;
import com.genealogy.dao.FamilyMemberDao;
import com.genealogy.dao.MediaResourceDao;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 *   多媒体资源服务层测试类
 * </p>
 * @author xiongyou
 */
@ExtendWith(MockitoExtension.class)
public class MediaResourceServiceImplTest {

    @Mock
    private MediaResourceDao mediaResourceDao;

    @Mock
    private FamilyMemberDao familyMemberDao;

    @InjectMocks
    private MediaResourceServiceImpl mediaResourceService;

    private MediaResourceDTO mediaResourceDTO;
    private MediaResourceQuery mediaResourceQuery;
    private FamilyMember familyMember;
    private MediaResource mediaResource;

    @BeforeEach
    public void setUp() {
        mediaResourceDTO = new MediaResourceDTO();
        mediaResourceDTO.setMemberId(1L);
        mediaResourceDTO.setFileName("test.jpg");
        mediaResourceDTO.setFilePath("/path/to/test.jpg");
        mediaResourceDTO.setFileType("image");
        mediaResourceDTO.setFileSize(1024L);
        mediaResourceDTO.setDescription("Test description");

        mediaResourceQuery = new MediaResourceQuery();
        mediaResourceQuery.setMemberId(1L);
        mediaResourceQuery.setResourceId(1L);

        familyMember = new FamilyMember();
        familyMember.setMemberId(1L);

        mediaResource = new MediaResource();
        mediaResource.setResourceId(1L);
        mediaResource.setMemberId(1L);
        mediaResource.setFileName("test.jpg");
        mediaResource.setFilePath("/path/to/test.jpg");
        mediaResource.setFileType("image");
        mediaResource.setFileSize(1024L);
        mediaResource.setUploadTime(new Timestamp(System.currentTimeMillis()));
        mediaResource.setUploadBy("testUser");
        mediaResource.setDescription("Test description");
        mediaResource.setIsDeleted(0);
    }

    @Test
    public void testUploadMediaResource_Success() {
        Mockito.when(familyMemberDao.findById(mediaResourceDTO.getMemberId())).thenReturn(Optional.of(familyMember));
        Mockito.when(mediaResourceDao.save(Mockito.any(MediaResource.class))).thenReturn(mediaResource);

        RestResult<Long> result = mediaResourceService.uploadMediaResource(mediaResourceDTO);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        assertEquals(mediaResource.getResourceId(), result.getData());
    }

    @Test
    public void testUploadMediaResource_UnsupportedFileType() {
        mediaResourceDTO.setFileType("unsupported");
        RestResult<Long> result = mediaResourceService.uploadMediaResource(mediaResourceDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("文件类型不支持", result.getMsg());
    }

    @Test
    public void testUploadMediaResource_MemberNotFound() {
        Mockito.when(familyMemberDao.findById(mediaResourceDTO.getMemberId())).thenReturn(Optional.empty());
        RestResult<Long> result = mediaResourceService.uploadMediaResource(mediaResourceDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员不存在", result.getMsg());
    }

    @Test
    public void testQueryMediaResources_Success() {
        Mockito.when(mediaResourceDao.findByMemberId(mediaResourceQuery.getMemberId())).thenReturn(Arrays.asList(mediaResource));
        RestResult<List<MediaResource>> result = mediaResourceService.queryMediaResources(mediaResourceQuery);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
        assertEquals(1, result.getData().size());
    }

    @Test
    public void testQueryMediaResources_InvalidMemberId() {
        mediaResourceQuery.setMemberId(null);
        RestResult<List<MediaResource>> result = mediaResourceService.queryMediaResources(mediaResourceQuery);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("成员ID无效", result.getMsg());
    }

    @Test
    public void testDeleteMediaResource_Success() {
        Mockito.when(mediaResourceDao.findByResourceId(mediaResourceQuery.getResourceId())).thenReturn(mediaResource);
        RestResult<Void> result = mediaResourceService.deleteMediaResource(mediaResourceQuery);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
    }

    @Test
    public void testDeleteMediaResource_ResourceNotFound() {
        Mockito.when(mediaResourceDao.findByResourceId(mediaResourceQuery.getResourceId())).thenReturn(null);
        RestResult<Void> result = mediaResourceService.deleteMediaResource(mediaResourceQuery);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("资源不存在", result.getMsg());
    }

    @Test
    public void testUpdateMediaResourceDescription_Success() {
        Mockito.when(mediaResourceDao.findByResourceId(mediaResourceDTO.getResourceId())).thenReturn(mediaResource);
        RestResult<Void> result = mediaResourceService.updateMediaResourceDescription(mediaResourceDTO);
        assertEquals(ResultCodeConstant.CODE_000000, result.getCode());
        assertEquals("调用成功", result.getMsg());
    }

    @Test
    public void testUpdateMediaResourceDescription_ResourceNotFound() {
        Mockito.when(mediaResourceDao.findByResourceId(mediaResourceDTO.getResourceId())).thenReturn(null);
        RestResult<Void> result = mediaResourceService.updateMediaResourceDescription(mediaResourceDTO);
        assertEquals(ResultCodeConstant.CODE_000001, result.getCode());
        assertEquals("资源不存在", result.getMsg());
    }
}