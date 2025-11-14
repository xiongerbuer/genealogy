package com.genealogy.dao;

import com.genealogy.entity.FamilyTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *   族谱树数据访问层
 * </p>
 * @author xiongyou
 */
@Repository
public interface FamilyTreeDao extends JpaRepository<FamilyTree, Long> {

    /**
     * 根据族谱名称查找族谱树
     * @param treeName 族谱名称
     * @return 族谱树
     */
    Optional<FamilyTree> findByTreeName(String treeName);

    /**
     * 根据族谱名称查找族谱树列表
     * @param treeName 族谱名称
     * @return 族谱树列表
     */
    @Query("SELECT ft FROM FamilyTree ft WHERE ft.treeName LIKE %:treeName% AND ft.isDeleted = :isDeleted")
    List<FamilyTree> findByTreeNameContainingAndIsDeleted(@Param("treeName") String treeName, @Param("isDeleted") Integer isDeleted);
}
