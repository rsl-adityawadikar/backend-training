package com.example.leave_management.dto;

import com.example.leave_management.model.LeaveType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class LeaveRequestDto {
	@NotBlank(message = "employeeId is required")
	private String employeeId;

	@NotNull(message = "leaveType is required")
	private LeaveType leaveType;

	@NotNull(message = "startDate is required")
	private LocalDate startDate;

	@NotNull(message = "endDate is required")
	private LocalDate endDate;

	@NotBlank(message = "reason is required")
	@Size(min = 3, max = 500, message = "reason must be between 3 and 500 characters")
	private String reason;

	public LeaveRequestDto() {
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
