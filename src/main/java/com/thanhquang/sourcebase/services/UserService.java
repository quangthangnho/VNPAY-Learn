package com.thanhquang.sourcebase.services;

import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface UserService {

    UserEntity findByEmailAndDeletedAtIsNull(String email) throws BadRequestException;

    boolean existsByEmailAndDeletedAtIsNull(String email);

    boolean existsByPhoneNumberAndDeletedAtIsNull(String phone);

    UserEntity save(UserEntity user);
}
