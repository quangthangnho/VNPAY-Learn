package com.thanhquang.sourcebase.services.impl;

import org.springframework.stereotype.Service;

import com.thanhquang.sourcebase.entities.UserRoleEntity;
import com.thanhquang.sourcebase.repositories.UserRoleRepository;
import com.thanhquang.sourcebase.services.UserRoleService;

@Service
public class UserRoleSeriviceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleSeriviceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void save(UserRoleEntity userRole) {
        userRoleRepository.save(userRole);
    }
}
