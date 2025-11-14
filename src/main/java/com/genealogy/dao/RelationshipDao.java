package com.genealogy.dao;

import com.genealogy.entity.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   亲属关系数据访问层
 * </p>
 * @author xiongyou
 */
@Repository
public interface RelationshipDao extends JpaRepository<Relationship, Long> {

    /**
     * 根据关系ID查询亲属关系
     * @param relationId 关系ID
     * @return 关系对象
     */
    Optional<Relationship> findByRelationIdAndIsDeleted(Long relationId, Integer isDeleted);

    /**
     * 根据起始成员ID和目标成员ID查询亲属关系
     * @param fromMemberId 起始成员ID
     * @param toMemberId 目标成员ID
     * @return 关系对象
     */
    Optional<Relationship> findByFromMemberIdAndToMemberIdAndIsDeleted(Long fromMemberId, Long toMemberId, Integer isDeleted);

    /**
     * 根据条件查询亲属关系列表
     * @param fromMemberId 起始成员ID
     * @param toMemberId 目标成员ID
     * @param relationType 关系类型
     * @return 关系对象列表
     */
    @Query("SELECT r FROM Relationship r WHERE (:fromMemberId IS NULL OR r.fromMemberId = :fromMemberId) "
            + "AND (:toMemberId IS NULL OR r.toMemberId = :toMemberId) "
            + "AND (:relationType IS NULL OR r.relationType = :relationType) "
            + "AND r.isDeleted = 0")
    List<Relationship> findByConditions(@Param("fromMemberId") Long fromMemberId, @Param("toMemberId") Long toMemberId, @Param("relationType") String relationType);

    // 通过JOIN关联FamilyMember表来获取treeId信息
//    @Query("SELECT m FROM FamilyMember m JOIN Relationship r ON m.memberId = r.fromMemberId " +
//            "WHERE r.relationType = :relationType AND m.treeId = :treeId AND m.isDeleted = 0")

}
