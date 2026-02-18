package com.mix.sale.next.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mix.sale.next.entity.MenuEntity;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    @Query("select t from MenuEntity t where t.roleId in (?1, 0) order by t.sortSeq")
    public List<MenuEntity> findByRoleIdAndDefault(Long roleId);

    public List<MenuEntity> findAllByOrderBySortSeq();
}
