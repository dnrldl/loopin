package com.loopin.loopinbackend.global.error;

import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    // 잘못된 경로로 요청
    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFound(Exception e) {
        log.info(e.getMessage());
        ApiErrorResponse body = ApiErrorResponse.from(ErrorCode.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


    // 잘못된 메서드로 요청 예) GET 으로 요청해야하는데 POST 로 요청
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiErrorResponse.from(ErrorCode.METHOD_NOT_ALLOWED));
    }


    // @RequestBody DTO 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleRequestBodyValidation(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> String.format("[%s] %s", err.getField(), err.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        ApiErrorResponse body = ApiErrorResponse.of(
                false,
                ErrorCode.INVALID_INPUT_VALUE.getCode(),
                message,
                ErrorCode.INVALID_INPUT_VALUE.getStatus().value()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // @RequestParam, @PathVariable, @Validated 파라미터 검증 실패
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleParamValidation(ConstraintViolationException e) {

        String message = e.getConstraintViolations().stream()
                .map(v -> String.format("[%s] %s",
                        ((PathImpl)v.getPropertyPath()).getLeafNode().getName(),
                        v.getMessage()))
                .collect(Collectors.joining("; "));

        ApiErrorResponse body = ApiErrorResponse.of(
                false,
                ErrorCode.INVALID_INPUT_VALUE.getCode(),
                message,
                ErrorCode.INVALID_INPUT_VALUE.getStatus().value()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // BaseException (비즈니스 예외)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("[{}] {}: {}", errorCode.getCode(), errorCode.getStatus(), errorCode.getMessage(), e);

        ApiErrorResponse body = ApiErrorResponse.from(e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(body);
    }

    // 서버 내부 예기치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        log.error("Unexpected error: ", e);
        ApiErrorResponse body = ApiErrorResponse.from(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(body);
    }
}
