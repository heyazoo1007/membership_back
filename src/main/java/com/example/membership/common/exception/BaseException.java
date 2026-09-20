package com.example.membership.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BaseException extends RuntimeException {

	public static final BaseException DUPLICATE_USER_MEMBERSHIP = new BaseException(ErrorCode.DUPLICATE_USER_MEMBERSHIP);
	public static final BaseException OUT_OF_MEMBERSHIP_OCCUPATION = new BaseException(ErrorCode.OUT_OF_MEMBERSHIP_OCCUPATION);
	public static final BaseException USER_NOT_FOUND = new BaseException(ErrorCode.USER_NOT_FOUND);
	public static final BaseException MEMBERSHIP_NOT_FOUND = new BaseException(ErrorCode.MEMBERSHIP_NOT_FOUND);



	private final ErrorCode errorCode;

	// 의도적인 예외이므로 stack trace 제거 (불필요한 예외처리 비용 제거)
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public HttpStatus getHttpStatus() {
		return errorCode.getHttpStatus();
	}
}
