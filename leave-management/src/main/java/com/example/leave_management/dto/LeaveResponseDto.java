package com.example.leave_management.dto;

import com.example.leave_management.model.LeaveStatus;
import com.example.leave_management.model.LeaveType;

import java.time.LocalDate;

public record LeaveResponseDto(
		Long id,
		String employeeId,
		LeaveType leaveType,
		LocalDate startDate,
		LocalDate endDate,
		String reason,
		LeaveStatus status) {
}
