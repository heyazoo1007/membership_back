package com.example.membership.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	
	/* 400 */
	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "Failed at validating input value."),
	DUPLICATE_USER_MEMBERSHIP(HttpStatus.BAD_REQUEST, "DUPLICATE_USER_MEMBERSHIP", "You've already joined this membership."),
	OUT_OF_MEMBERSHIP_OCCUPATION(HttpStatus.BAD_REQUEST, "OUT_OF_MEMBERSHIP_OCCUPATION", "Selected Membership is out of occupation."),

	/* 401 */
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "유효한 인증 정보가 아닙니다."),
	EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_ACCESS_TOKEN", "Access Token이 만료되었습니다. 토큰을 재발급해주세요"),

	/* 403 */
	FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근할 수 있는 권한이 없습니다."),
	EXPIRED_OR_PREVIOUS_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "EXPIRED_OR_PREVIOUS_REFRESH_TOKEN", "만료되었거나 이전에 발급된 Refresh Token입니다."),

	/* 404 */
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User doesn't exist."),
	MEMBERSHIP_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBERSHIP_NOT_FOUND", "Membership doesn't exist."),

	/* 500 */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "예상치 못한 서버 에러가 발생했습니다.");


	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
