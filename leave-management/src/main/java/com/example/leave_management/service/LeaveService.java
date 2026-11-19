package com.example.leave_management.service;

import com.example.leave_management.dto.LeaveRequestDto;
import com.example.leave_management.dto.LeaveResponseDto;
import com.example.leave_management.dto.LeaveUpdateDto;

import java.util.List;

public interface LeaveService {
	List<LeaveResponseDto> getAllLeaves();

	LeaveResponseDto getLeaveById(Long id);

	LeaveResponseDto createLeave(LeaveRequestDto request);

	LeaveResponseDto updateLeave(Long id, LeaveUpdateDto request);

	void deleteLeave(Long id);
}
