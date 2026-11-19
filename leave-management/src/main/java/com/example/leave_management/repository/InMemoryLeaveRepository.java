package com.example.leave_management.repository;

import com.example.leave_management.model.LeaveRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryLeaveRepository implements LeaveRepository {
	private final ConcurrentHashMap<Long, LeaveRequest> leaves = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong();

	@Override
	public List<LeaveRequest> findAll() {
		return new ArrayList<>(leaves.values());
	}

	@Override
	public Optional<LeaveRequest> findById(Long id) {
		return Optional.ofNullable(leaves.get(id));
	}

	@Override
	public LeaveRequest save(LeaveRequest leaveRequest) {
		if (leaveRequest.getId() == null) {
			leaveRequest.setId(sequence.incrementAndGet());
		}
		leaves.put(leaveRequest.getId(), leaveRequest);
		return leaveRequest;
	}

	@Override
	public boolean deleteById(Long id) {
		return leaves.remove(id) != null;
	}
}
