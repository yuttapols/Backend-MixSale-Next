package com.mix.sale.next.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDTO {
	private String accessToken;
	private String token;
}
