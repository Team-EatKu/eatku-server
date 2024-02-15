package eatku.eatkuserver.global.error.exception;

import eatku.eatkuserver.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
