package com.loopin.loopinbackend.global.error;

import com.loopin.loopinbackend.global.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // BaseException (비즈니스 예외)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException e) {

        ApiErrorResponse body = ApiErrorResponse.from(e.getErrorCode());
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(body);
    }

    // @RequestBody DTO 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleRequestBodyValidation(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> String.format("[%s] %s", err.getField(), err.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        ApiErrorResponse body = new ApiErrorResponse(
                false,
                ErrorCode.INVALID_INPUT_VALUE.getCode(),
                message,
                HttpStatus.BAD_REQUEST.value()
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

        ApiErrorResponse body = new ApiErrorResponse(
                false,
                ErrorCode.INVALID_INPUT_VALUE.getCode(),
                message,
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    // 예기치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception e) {

        log.error("Unexpected error: ", e);
        ApiErrorResponse body = new ApiErrorResponse(
                false,
                "E000",
                "알 수 없는 오류가 발생했습니다.",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}
