package com.mix.sale.next.dto.res;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SubMenuResDTO {

    private Long id;
    private Long menuId;
    private String menuNameTh;
    private String menuNameEng;
    private String menuIconName;
    private String menuPathRouterLink;
    private String status;
    private String createBy;
    private Timestamp createDate;
    private String updateBy;
    private Timestamp updateDate;
}
