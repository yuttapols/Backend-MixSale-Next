package com.mix.sale.next.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mix.sale.next.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long>{

	@Query("select t from RefreshTokenEntity t where t.token = ?1")
    public RefreshTokenEntity findByToken(String token);
	
	@Query("select t from RefreshTokenEntity t where t.userId = ?1")
    public RefreshTokenEntity findByUserId(Long userId);
    
    @Modifying
    @Query("delete from RefreshTokenEntity t where t.userId = ?1")
    void deleteByUserId(Long userId);
}
