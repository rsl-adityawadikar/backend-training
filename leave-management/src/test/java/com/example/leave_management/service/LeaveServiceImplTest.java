package com.example.leave_management.service;

import com.example.leave_management.dto.LeaveRequestDto;
import com.example.leave_management.dto.LeaveResponseDto;
import com.example.leave_management.exception.InvalidLeaveRequestException;
import com.example.leave_management.exception.LeaveNotFoundException;
import com.example.leave_management.model.LeaveStatus;
import com.example.leave_management.model.LeaveType;
import com.example.leave_management.repository.InMemoryLeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LeaveServiceImplTest {
	private LeaveServiceImpl service;

	@BeforeEach
	void setUp() {
		service = new LeaveServiceImpl(new InMemoryLeaveRepository());
	}

	@Test
	void createLeaveSetsPendingStatusAndId() {
		LeaveRequestDto request = createRequest();

		LeaveResponseDto created = service.createLeave(request);

		assertEquals(1L, created.id());
		assertEquals(LeaveStatus.PENDING, created.status());
		assertEquals("EMP-001", created.employeeId());
	}

	@Test
	void getLeaveByIdThrowsWhenMissing() {
		assertThrows(LeaveNotFoundException.class, () -> service.getLeaveById(999L));
	}

	@Test
	void createLeaveRejectsEndDateBeforeStartDate() {
		LeaveRequestDto request = createRequest();
		request.setEndDate(request.getStartDate().minusDays(1));

		assertThrows(InvalidLeaveRequestException.class, () -> service.createLeave(request));
	}

	private LeaveRequestDto createRequest() {
		LeaveRequestDto request = new LeaveRequestDto();
		request.setEmployeeId("EMP-001");
		request.setLeaveType(LeaveType.CASUAL);
		request.setStartDate(LocalDate.now().plusDays(1));
		request.setEndDate(LocalDate.now().plusDays(3));
		request.setReason("Personal appointment");
		return request;
	}
}
