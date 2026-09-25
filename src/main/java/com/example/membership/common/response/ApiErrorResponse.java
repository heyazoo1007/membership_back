package com.example.membership.common.response;

import com.example.membership.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;

	public static ApiErrorResponse from(ErrorCode errorCode) {
		return ApiErrorResponse.builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.build();
	}

	public void changeMessage(String message) {
		this.message = message;
	}
}
