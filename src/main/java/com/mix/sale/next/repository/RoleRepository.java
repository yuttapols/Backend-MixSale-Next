package com.mix.sale.next.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mix.sale.next.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("select t from RoleEntity t where t.status = '1' ")
    public List<RoleEntity> findByStatusIsActive();
}
