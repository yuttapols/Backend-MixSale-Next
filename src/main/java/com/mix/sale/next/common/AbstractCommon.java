package com.mix.sale.next.common;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.mix.sale.next.dto.res.UserDetailResDTO;
import com.mix.sale.next.entity.AuthenticationEntity;
import com.mix.sale.next.payload.ApiResponse;
import com.mix.sale.next.payload.CustomerUserAttr;
import com.mix.sale.next.repository.AuthenticationRepository;
import com.mix.sale.next.repository.UserDetailRepository;
import com.mix.sale.next.service.JwtService;
import com.mix.sale.next.util.AppConstants;
import com.mix.sale.next.util.Constants;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractCommon {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationRepository authenticationRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    private ModelMapper mapper;

    protected final static ApiResponse getSavedSuccessResponse() {
        return ApiResponse.builder()
                .success(true)
                .code(Integer.toString(HttpStatus.OK.value()))
                .message(Constants.MESSAGE_KEYS_SAVE_ENG)
                .messageTh(Constants.MESSAGE_KEYS_SAVE_TH)
                .build();
    }

    protected final static ApiResponse getOkResponse(HttpStatus code, String message) {
        return ApiResponse.builder()
                .success(true)
                .code(Integer.toString(code.value()))
                .message(message)
                .build();
    }

    protected final static ApiResponse getOkResponse() {

        return ApiResponse.builder()
                .success(true)
                .code(Integer.toString(HttpStatus.OK.value()))
                .message(Constants.MESSAGE_KEY_ENG)
                .messageTh(Constants.MESSAGE_KEY_TH)
                .build();
    }

    protected final static <T> ApiResponse getOkResponseData(T body) {

        return ApiResponse.builder()
                .success(true)
                .code(Integer.toString(HttpStatus.OK.value()))
                .data(body)
                .message(Constants.MESSAGE_KEY_ENG)
                .messageTh(Constants.MESSAGE_KEY_TH)
                .build();
    }

    public CustomerUserAttr getCustomerUserAttr(HttpServletRequest request) {
        CustomerUserAttr customerUserAttr = new CustomerUserAttr();
        UserDetails userDetail = (UserDetails) jwtService.getAuthentication(getToken(request)).getPrincipal();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(userDetail.getUsername())) {
            Optional<AuthenticationEntity> dataList = authenticationRepository.findByUserName(userDetail.getUsername());
            if (dataList.isPresent()) {
                UserDetailResDTO userDetails = new UserDetailResDTO();
                if (ObjectUtils.isNotEmpty(dataList.get().getId())) {
                    userDetails = mapper.map(userDetailRepository.findByUserId(dataList.get().getId()), new TypeToken<UserDetailResDTO>() {
                    }.getType());
                }
                customerUserAttr.setCustomerNo(userDetails.getCustomerNo());
                customerUserAttr.setCustomerName(userDetails.getFristName() + "-" + userDetails.getLastName());
                customerUserAttr.setRoleId(dataList.get().getRoleId());
                customerUserAttr.setRoleName(dataList.get().getRole().getRoleNameTh());
            }
        }

        customerUserAttr.setIpAddress(request.getRemoteAddr());

        return customerUserAttr;
    }

    public String getToken(HttpServletRequest request) {
        String token = null;
        String bearerToken = request.getHeader(AppConstants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return token;
    }
}
