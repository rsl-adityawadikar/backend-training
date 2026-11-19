package com.example.leave_management.service;

import com.example.leave_management.dto.LeaveRequestDto;
import com.example.leave_management.dto.LeaveResponseDto;
import com.example.leave_management.dto.LeaveUpdateDto;
import com.example.leave_management.exception.InvalidLeaveRequestException;
import com.example.leave_management.exception.LeaveNotFoundException;
import com.example.leave_management.model.LeaveRequest;
import com.example.leave_management.model.LeaveStatus;
import com.example.leave_management.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
	private final LeaveRepository repository;

	public LeaveServiceImpl(LeaveRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<LeaveResponseDto> getAllLeaves() {
		return repository.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	public LeaveResponseDto getLeaveById(Long id) {
		return repository.findById(id).map(this::toResponse)
				.orElseThrow(() -> new LeaveNotFoundException(id));
	}

	@Override
	public LeaveResponseDto createLeave(LeaveRequestDto request) {
		validateDates(request.getStartDate(), request.getEndDate());
		LeaveRequest leave = new LeaveRequest(
				null,
				request.getEmployeeId().trim(),
				request.getLeaveType(),
				request.getStartDate(),
				request.getEndDate(),
				request.getReason().trim(),
				LeaveStatus.PENDING);
		return toResponse(repository.save(leave));
	}

	@Override
	public LeaveResponseDto updateLeave(Long id, LeaveUpdateDto request) {
		validateDates(request.getStartDate(), request.getEndDate());
		LeaveRequest leave = repository.findById(id)
				.orElseThrow(() -> new LeaveNotFoundException(id));
		leave.setEmployeeId(request.getEmployeeId().trim());
		leave.setLeaveType(request.getLeaveType());
		leave.setStartDate(request.getStartDate());
		leave.setEndDate(request.getEndDate());
		leave.setReason(request.getReason().trim());
		leave.setStatus(request.getStatus());
		return toResponse(repository.save(leave));
	}

	@Override
	public void deleteLeave(Long id) {
		if (!repository.deleteById(id)) {
			throw new LeaveNotFoundException(id);
		}
	}

	private void validateDates(java.time.LocalDate startDate, java.time.LocalDate endDate) {
		if (endDate.isBefore(startDate)) {
			throw new InvalidLeaveRequestException("endDate must be on or after startDate");
		}
	}

	private LeaveResponseDto toResponse(LeaveRequest leave) {
		return new LeaveResponseDto(
				leave.getId(),
				leave.getEmployeeId(),
				leave.getLeaveType(),
				leave.getStartDate(),
				leave.getEndDate(),
				leave.getReason(),
				leave.getStatus());
	}
}
