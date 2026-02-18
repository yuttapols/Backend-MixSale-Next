package com.mix.sale.next.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mix.sale.next.common.AbstractCommon;
import com.mix.sale.next.dto.res.JwtResponseDTO;
import com.mix.sale.next.dto.res.RefreshTokenDTO;
import com.mix.sale.next.payload.ApiResponse;
import com.mix.sale.next.repository.AuthenticationRepository;
import com.mix.sale.next.service.AuthenService;
import com.mix.sale.next.service.JwtService;
import com.mix.sale.next.service.RefreshTokenService;
import com.mix.sale.next.util.AppConstants;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = AppConstants.PROJECT_VERSION + "/authentication")
@RequiredArgsConstructor
public class AuthenticationController extends AbstractCommon {

    @Autowired
    private AuthenService authenService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    JwtService jwtService;

    @GetMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) throws Exception {
        ApiResponse response;
        try {
            response = getOkResponseData(authenService.login(userName, password));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestParam(name = "token") String token) {

        RefreshTokenDTO refreshTokenDTO = refreshTokenService.findByToken(token);

        if (ObjectUtils.isNotEmpty(refreshTokenDTO)) {
            refreshTokenDTO = refreshTokenService.verifyExpiration(refreshTokenDTO);
            if (ObjectUtils.isNotEmpty(refreshTokenDTO)) {
                var user = authenticationRepository.findById(refreshTokenDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
                var jwt = jwtService.generateToken(user);
                return JwtResponseDTO.builder()
                        .accessToken(jwt)
                        .token(token).build();
            }
        } else {
            throw new RuntimeException("Refresh Token is not in DB..!!");
        }

        return JwtResponseDTO.builder().build();
    }
}
