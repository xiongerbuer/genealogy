package com.genealogy.dao;

import com.genealogy.entity.FamilyMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberDao extends JpaRepository<FamilyMember, Long> {

    List<FamilyMember> findByTreeId(Long treeId);

    @EntityGraph(attributePaths = { "tree" })
    Optional<FamilyMember> findByMemberIdAndIsDeleted(@Param("memberId") Long memberId, @Param("isDeleted") Integer isDeleted);

    /**
     * 根据姓名和族谱ID查询成员
     * @param name 姓名
     * @param treeId 族谱ID
     * @return 成员列表
     */
    @Query("SELECT m FROM FamilyMember m WHERE m.name = :name AND m.treeId = :treeId AND m.isDeleted = 0")
    List<FamilyMember> findByNameAndTreeId(@Param("name") String name, @Param("treeId") Long treeId);

    /**
     * 根据关系类型和族谱ID查询成员
     * @param relationType 关系类型
     * @param treeId 族谱ID
     * @return 成员列表
     */
//    @Query("SELECT m FROM FamilyMember m WHERE m.memberId IN (SELECT r.fromMemberId FROM Relationship r WHERE r.relationType = :relationType AND r.treeId = :treeId) AND m.isDeleted = 0")
//    List<FamilyMember> findByRelationTypeAndTreeId(@Param("relationType") String relationType, @Param("treeId") Long treeId);

    // 使用 @Query 注解自定义正确的HQL查询
    @Query("SELECT m FROM FamilyMember m WHERE m.memberId IN " +
            "(SELECT r.fromMemberId FROM Relationship r WHERE r.relationType = :relationType AND r.fromMemberId IN " +
            "(SELECT fm.memberId FROM FamilyMember fm WHERE fm.treeId = :treeId)) AND m.isDeleted = 0")
    List<FamilyMember> findByRelationTypeAndTreeId(@Param("relationType") String relationType, @Param("treeId") Long treeId);

    /**
     * 根据出生日期范围和族谱ID筛选成员
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param treeId 族谱ID
     * @return 成员列表
     */
    @Query("SELECT m FROM FamilyMember m WHERE m.birthDate BETWEEN :startDate AND :endDate AND m.treeId = :treeId AND m.isDeleted = 0")
    List<FamilyMember> findByBirthDateBetweenAndTreeId(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("treeId") Long treeId);

    /**
     * 综合条件查询成员
     * @param name 姓名
     * @param relationType 关系类型
     * @param startDate 开始年份
     * @param endDate 结束年份
     * @param treeId 族谱ID
     * @return 成员列表
     */
    @Query("SELECT DISTINCT m FROM FamilyMember m LEFT JOIN Relationship r ON m.memberId = r.fromMemberId WHERE " + "(m.name LIKE %:name%) AND " + "(r.relationType = :relationType OR :relationType IS NULL) AND " + "(m.birthDate BETWEEN :startDate AND :endDate OR (:startDate IS NULL AND :endDate IS NULL)) AND " + "m.treeId = :treeId AND m.isDeleted = 0")
    List<FamilyMember> searchByMultipleConditions(@Param("name") String name, @Param("relationType") String relationType, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("treeId") Long treeId);

    @Query("SELECT COUNT(m) FROM FamilyMember m WHERE m.treeId = :treeId AND m.gender = :gender")
    long countByTreeIdAndGender(@Param("treeId") Long treeId, @Param("gender") Integer gender);

    @Query("SELECT COUNT(m) FROM FamilyMember m WHERE m.treeId = :treeId")
    long countByTreeId(@Param("treeId") Long treeId);

}
