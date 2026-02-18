package com.mix.sale.next.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mix.sale.next.common.AbstractCommon;
import com.mix.sale.next.payload.ApiResponse;
import com.mix.sale.next.service.RoleService;
import com.mix.sale.next.util.AppConstants;

@RestController
@RequestMapping(value = AppConstants.PROJECT_VERSION + "/role")
public class RoleController extends AbstractCommon {

    @Autowired
    RoleService roleService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll() {
        ApiResponse response;

        try {
            response = getOkResponseData(roleService.getAll());

        } catch (Exception e) {
            response = getOkResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
