package com.thanhquang.sourcebase.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.entities.UserRoleEntity;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByUser(UserEntity user);
}
