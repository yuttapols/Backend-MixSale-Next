package com.mix.sale.next.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mix.sale.next.dto.res.AuthenticationResDTO;
import com.mix.sale.next.dto.res.RefreshTokenDTO;
import com.mix.sale.next.dto.res.UserDetailResDTO;
import com.mix.sale.next.entity.AuthenticationEntity;
import com.mix.sale.next.repository.AuthenticationRepository;
import com.mix.sale.next.repository.UserDetailRepository;
import com.mix.sale.next.service.AuthenService;
import com.mix.sale.next.service.JwtService;
import com.mix.sale.next.service.RefreshTokenService;
import com.mix.sale.next.util.Md5Util;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenServiceImpl implements AuthenService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public AuthenticationResDTO login(String userName, String password) {

        Optional<AuthenticationEntity> userOpt = authenticationRepository.findByUserName(userName);
        if (userOpt.isPresent()) {
            AuthenticationEntity user = userOpt.get();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, passwordEncoder.encode(Md5Util.checkMd5(password))));

            var jwt = jwtService.generateToken(user);

            UserDetailResDTO userDetail = null;
            if (ObjectUtils.isNotEmpty(user)) {
                userDetail = mapper.map(userDetailRepository.findByUserId(user.getId()), new TypeToken<UserDetailResDTO>() {
                }.getType());
            }

            RefreshTokenDTO refreshTokenDTO = refreshTokenService.createRefreshToken(userName);

            return AuthenticationResDTO.builder()
                    .id(user.getId())
                    .userName(user.getUsername())
                    .status(user.getStatus())
                    .role(user.getRole())
                    .createBy(user.getCreateBy())
                    .createDate(user.getCreateDate())
                    .updateBy(user.getUpdateBy())
                    .updateDate(user.getUpdateDate())
                    .accessToken(jwt)
                    .token(refreshTokenDTO.getToken())
                    .userDetail(userDetail)
                    .build();
        }

        return null;
    }

}
