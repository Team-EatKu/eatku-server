package eatku.eatkuserver.global.error;

import eatku.eatkuserver.global.error.exception.BusinessException;
import eatku.eatkuserver.global.result.ResultResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, LocalDateTime.now());
        errorResponse.setDetail(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> notFoundApi(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>(ErrorResponse.of(ErrorCode.NOT_FOUNT_API, LocalDateTime.now()), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(ErrorResponse.of(ErrorCode.SERVER_ERROR, LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
