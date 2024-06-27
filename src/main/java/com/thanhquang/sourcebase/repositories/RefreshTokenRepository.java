package com.thanhquang.sourcebase.repositories;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thanhquang.sourcebase.entities.RefreshTokenEntity;
import com.thanhquang.sourcebase.entities.UserEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RefreshTokenEntity r WHERE r.user = :user ")
    void deleteTokenByUser(UserEntity user);
}
