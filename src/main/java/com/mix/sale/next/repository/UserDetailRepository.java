package com.mix.sale.next.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mix.sale.next.entity.UserDetailEntity;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    @Query("select t from UserDetailEntity t where t.userId = ?1")
    public UserDetailEntity findByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from UserDetailEntity t where t.userId = ?1")
    void deleteByUserId(Long userId);
}
