package eatku.eatkuserver.global.error.exception;


import eatku.eatkuserver.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
