package com.mix.sale.next.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mix.sale.next.dto.res.RoleResDTO;
import com.mix.sale.next.repository.RoleRepository;
import com.mix.sale.next.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResDTO> getAll() {
        return mapper.map(roleRepository.findByStatusIsActive(), new TypeToken<List<RoleResDTO>>() {
        }.getType());
    }
}
