package com.mix.sale.next.service;

import java.util.List;

import com.mix.sale.next.dto.req.MenuReqDTO;
import com.mix.sale.next.dto.res.MenuResDTO;
import com.mix.sale.next.payload.CustomerUserAttr;

public interface MenuService {

    public List<MenuResDTO> getAll();

    public List<MenuResDTO> getByRoleIdAndDefault(Long roleId);

    public void save(CustomerUserAttr userAttr, MenuReqDTO menuReq);

    public void update(CustomerUserAttr userAttr, MenuReqDTO menuReq, Long menuId);
}
