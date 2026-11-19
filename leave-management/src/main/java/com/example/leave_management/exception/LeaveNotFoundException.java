package com.example.leave_management.exception;

public class LeaveNotFoundException extends RuntimeException {
	public LeaveNotFoundException(Long id) {
		super("Leave request not found with id: " + id);
	}
}
