package com.mix.sale.next.service;

import com.mix.sale.next.dto.res.RefreshTokenDTO;

public interface RefreshTokenService {

	public RefreshTokenDTO findByToken(String token) ;
	public RefreshTokenDTO createRefreshToken(String userName);
	public RefreshTokenDTO verifyExpiration(RefreshTokenDTO token) ;
}
