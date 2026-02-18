package com.mix.sale.next.service;

import org.springframework.stereotype.Service;

import com.mix.sale.next.dto.res.AuthenticationResDTO;

@Service
public interface AuthenService {
	
	public AuthenticationResDTO login(String userName, String password) throws Exception;

}
