package com.genealogy.service;

import com.genealogy.common.response.RestResult;
import com.genealogy.dto.RelationshipDTO;
import com.genealogy.query.RelationshipQuery;
import java.util.List;

/**
 * <p>
 *   亲属关系服务接口
 * </p>
 * @author xiongyou
 */
public interface RelationshipService {

    /**
     * 新增亲属关系
     * @param relationshipDTO 亲属关系信息
     * @return 处理结果
     */
    RestResult<Long> addRelationship(RelationshipDTO relationshipDTO);

    /**
     * 删除亲属关系
     * @param relationshipQuery 亲属关系查询条件
     * @return 处理结果
     */
    RestResult<Void> deleteRelationship(RelationshipQuery relationshipQuery);

    /**
     * 修改亲属关系
     * @param relationshipDTO 亲属关系信息
     * @return 处理结果
     */
    RestResult<Void> updateRelationship(RelationshipDTO relationshipDTO);

    /**
     * 查询亲属关系列表
     * @param relationshipQuery 亲属关系查询条件
     * @return 亲属关系列表
     */
    RestResult<List<RelationshipDTO>> listRelationships(RelationshipQuery relationshipQuery);

    /**
     * 查询单个亲属关系详情
     * @param relationshipQuery 亲属关系查询条件
     * @return 亲属关系详情
     */
    RestResult<RelationshipDTO> getRelationship(RelationshipQuery relationshipQuery);
}