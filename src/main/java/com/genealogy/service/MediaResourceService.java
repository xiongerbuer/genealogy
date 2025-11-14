package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.MediaResourceDTO;
import com.genealogy.entity.MediaResource;
import com.genealogy.query.MediaResourceQuery;
import java.util.List;

/**
 * <p>
 *   多媒体资源服务层接口
 * </p>
 * @author xiongyou
 */
public interface MediaResourceService {

    /**
     * 上传多媒体资料
     * @param mediaResourceDTO 多媒体资料信息
     * @return RestResult结果
     */
    RestResult<Long> uploadMediaResource(MediaResourceDTO mediaResourceDTO);

    /**
     * 查询成员多媒体资料
     * @param mediaResourceQuery 查询条件
     * @return RestResult结果
     */
    RestResult<List<MediaResource>> queryMediaResources(MediaResourceQuery mediaResourceQuery);

    /**
     * 删除多媒体资料
     * @param mediaResourceQuery 查询条件
     * @return RestResult结果
     */
    RestResult<Void> deleteMediaResource(MediaResourceQuery mediaResourceQuery);

    /**
     * 更新多媒体资料描述
     * @param mediaResourceDTO 多媒体资料信息
     * @return RestResult结果
     */
    RestResult<Void> updateMediaResourceDescription(MediaResourceDTO mediaResourceDTO);
}