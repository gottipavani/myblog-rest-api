package com.myblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class BlogException extends Throwable {
    public BlogException(HttpStatus httpStatus, String invalidJwtSignature) {
    }
}
