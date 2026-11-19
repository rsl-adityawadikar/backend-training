package com.example.leave_management.exception;

public class InvalidLeaveRequestException extends RuntimeException {
	public InvalidLeaveRequestException(String message) {
		super(message);
	}
}
