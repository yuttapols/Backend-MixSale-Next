package com.mix.sale.next.dto.res;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RefreshTokenDTO {

	private Long id;
	private String token;
	private Long userId;
	private Timestamp expiryDate;
}
