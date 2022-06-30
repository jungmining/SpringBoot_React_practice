package com.min.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
// 데이터가 없을때마다 예외를 설정한다 
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8005357596276819294L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
