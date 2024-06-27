package com.thanhquang.sourcebase.services.impl;

import org.springframework.stereotype.Service;

import com.thanhquang.sourcebase.entities.RoleEntity;
import com.thanhquang.sourcebase.enums.user.Roles;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.AuthenticationErrors;
import com.thanhquang.sourcebase.repositories.RoleRepository;
import com.thanhquang.sourcebase.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleEntity findByRoleName(String roleName) throws BadRequestException {
        return roleRepository
                .findByRoleName(Roles.USER.name())
                .orElseThrow(() -> new BadRequestException(AuthenticationErrors.DEFAULT_ROLE_NOT_FOUND));
    }
}
