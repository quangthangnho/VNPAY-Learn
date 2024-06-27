package com.thanhquang.sourcebase.services.impl;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thanhquang.sourcebase.dto.request.auth.LoginDto;
import com.thanhquang.sourcebase.dto.request.auth.RegisterDto;
import com.thanhquang.sourcebase.dto.response.auth.JwtResDto;
import com.thanhquang.sourcebase.dto.response.user.UserDto;
import com.thanhquang.sourcebase.entities.RoleEntity;
import com.thanhquang.sourcebase.entities.UserEntity;
import com.thanhquang.sourcebase.entities.UserRoleEntity;
import com.thanhquang.sourcebase.enums.user.Roles;
import com.thanhquang.sourcebase.enums.user.UserStatus;
import com.thanhquang.sourcebase.exceptions.BadRequestException;
import com.thanhquang.sourcebase.exceptions.error_code.impl.AuthenticationErrors;
import com.thanhquang.sourcebase.exceptions.error_code.impl.UserErrors;
import com.thanhquang.sourcebase.mapper.AuthMapper;
import com.thanhquang.sourcebase.mapper.UserMapper;
import com.thanhquang.sourcebase.services.AuthService;
import com.thanhquang.sourcebase.services.RefreshTokenService;
import com.thanhquang.sourcebase.services.RoleService;
import com.thanhquang.sourcebase.services.UserService;
import com.thanhquang.sourcebase.utils.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    public AuthServiceImpl(
            RoleService roleService,
            PasswordEncoder passwordEncoder,
            AuthenticationProvider authenticationProvider,
            RefreshTokenService refreshTokenService,
            UserService userService) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDto register(RegisterDto registerDto) throws BadRequestException {
        if (userService.existsByEmailAndDeletedAtIsNull(registerDto.getEmail())) {
            throw new BadRequestException(UserErrors.EMAIL_EXISTS);
        }
        if (userService.existsByPhoneNumberAndDeletedAtIsNull(registerDto.getPhoneNumber())) {
            throw new BadRequestException(UserErrors.PHONE_EXISTS);
        }
        UserEntity user = AuthMapper.INSTANCE.toUserEntityRegister(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        UserRoleEntity userRole =
                UserRoleEntity.builder().user(user).role(getDefaultRole()).build();
        user.setUserRoles(Set.of(userRole));
        return UserMapper.INSTANCE.toUserDto(userService.save(user));
    }

    @Override
    public JwtResDto login(LoginDto loginDto) throws BadRequestException {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (Exception e) {
            throw new BadRequestException(AuthenticationErrors.EMAIL_OR_PASSWORD_INVALID);
        }
        UserEntity user = userService.findByEmailAndDeletedAtIsNull(loginDto.getEmail());

        if (user.getStatus() == UserStatus.DEACTIVATED) {
            throw new BadRequestException(UserErrors.USER_DEACTIVATED);
        }
        String accessToken = JwtUtils.generateToken(user.getEmail(), true).getToken();
        return new JwtResDto(accessToken, refreshTokenService.createRefreshToken(user.getEmail()));
    }

    @Override
    public JwtResDto refreshToken(String refreshToken) throws BadRequestException {
        refreshTokenService.verifyRefreshTokenExpiration(refreshToken);
        String accessToken = JwtUtils.generateToken(
                        JwtUtils.getEmailFromToken(refreshToken)
                                .orElseThrow(() -> new BadRequestException(AuthenticationErrors.TOKEN_INVALID)),
                        true)
                .getToken();
        return new JwtResDto(accessToken, refreshToken);
    }

    private RoleEntity getDefaultRole() throws BadRequestException {
        return roleService.findByRoleName(Roles.USER.name());
    }
}
