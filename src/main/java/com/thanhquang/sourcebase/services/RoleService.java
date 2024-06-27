package com.thanhquang.sourcebase.services;

import com.thanhquang.sourcebase.entities.RoleEntity;
import com.thanhquang.sourcebase.exceptions.BadRequestException;

public interface RoleService {

    RoleEntity findByRoleName(String roleName) throws BadRequestException;
}
