package com.mix.sale.next.dto.res;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RoleResDTO {

    private Long id;
    private String roleNameEng;
    private String roleNameTh;
    private String status;
    private String createBy;
    private Timestamp createDate;
    private String updateBy;
    private Timestamp updateDate;
}
