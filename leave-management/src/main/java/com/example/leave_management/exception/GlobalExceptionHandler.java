package com.example.leave_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(LeaveNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(LeaveNotFoundException ex, HttpServletRequest request) {
		return response(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of(), request);
	}

	@ExceptionHandler(InvalidLeaveRequestException.class)
	public ResponseEntity<ErrorResponse> handleInvalid(InvalidLeaveRequestException ex, HttpServletRequest request) {
		return response(HttpStatus.BAD_REQUEST, ex.getMessage(), Map.of(), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.put(error.getField(), error.getDefaultMessage());
		}
		return response(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors, request);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		return response(HttpStatus.BAD_REQUEST, "Malformed JSON or invalid enum value", Map.of(), request);
	}

	private ResponseEntity<ErrorResponse> response(HttpStatus status, String message,
			Map<String, String> fieldErrors, HttpServletRequest request) {
		ErrorResponse body = new ErrorResponse(
				LocalDateTime.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				fieldErrors,
				request.getRequestURI());
		return ResponseEntity.status(status).body(body);
	}
}
