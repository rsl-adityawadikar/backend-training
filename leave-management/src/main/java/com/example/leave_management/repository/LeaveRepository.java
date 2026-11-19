package com.example.leave_management.repository;

import com.example.leave_management.model.LeaveRequest;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository {
	List<LeaveRequest> findAll();

	Optional<LeaveRequest> findById(Long id);

	LeaveRequest save(LeaveRequest leaveRequest);

	boolean deleteById(Long id);
}
