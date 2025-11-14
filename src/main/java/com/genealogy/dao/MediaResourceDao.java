package com.genealogy.dao;

import com.genealogy.entity.MediaResource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *   多媒体资源数据访问层
 * </p>
 * @author xiongyou
 */
public interface MediaResourceDao extends JpaRepository<MediaResource, Long> {

    /**
     * 根据成员ID查询关联的多媒体资料列表
     * @param memberId 成员ID
     * @return 多媒体资料列表
     */
    @Query("SELECT m FROM MediaResource m WHERE m.memberId = :memberId AND m.isDeleted = 0")
    List<MediaResource> findByMemberId(@Param("memberId") Long memberId);

    /**
     * 根据资源ID查询多媒体资料
     * @param resourceId 资源ID
     * @return 多媒体资料
     */
    @Query("SELECT m FROM MediaResource m WHERE m.resourceId = :resourceId AND m.isDeleted = 0")
    MediaResource findByResourceId(@Param("resourceId") Long resourceId);
}