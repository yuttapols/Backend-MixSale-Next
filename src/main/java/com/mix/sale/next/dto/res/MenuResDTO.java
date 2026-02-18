package com.mix.sale.next.dto.res;

import java.sql.Timestamp;
import java.util.List;

import com.mix.sale.next.entity.SubMenuEntity;

import lombok.Data;

@Data
public class MenuResDTO {

    private Long id;
    private Long roleId;
    private String menuNameTh;
    private String menuNameEng;
    private String menuClassId;
    private String menuIconName;
    private String menuPathRouterLink;
    private String isDropdown;
    private String status;
    private String createBy;
    private Timestamp createDate;
    private String updateBy;
    private Timestamp updateDate;
    private List<SubMenuEntity> subMenu;
}
