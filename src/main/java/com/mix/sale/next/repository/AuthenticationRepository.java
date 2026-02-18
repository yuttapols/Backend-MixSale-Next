package com.mix.sale.next.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mix.sale.next.entity.AuthenticationEntity;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {

    @Query("select t from AuthenticationEntity t where t.userName =?1 and t.password = ?2")
    public AuthenticationEntity findByUserNameAndPassword(String userName, String password);

    @Query("select t from AuthenticationEntity t where t.userName =?1")
    Optional<AuthenticationEntity> findByUserName(String userName);

    @Query("select t from AuthenticationEntity t where t.roleId = 2")
    public List<AuthenticationEntity> findAllByCustomer();

    @Modifying(clearAutomatically = true)
    @Query("delete from AuthenticationEntity t where t.id = ?1")
    void deleteByUserId(Long userId);
}
