package com.example.leave_management.controller;

import com.example.leave_management.exception.GlobalExceptionHandler;
import com.example.leave_management.repository.InMemoryLeaveRepository;
import com.example.leave_management.service.LeaveServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LeaveControllerTest {
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		LeaveServiceImpl service = new LeaveServiceImpl(new InMemoryLeaveRepository());
		mockMvc = MockMvcBuilders
				.standaloneSetup(new LeaveController(service))
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	void createLeaveReturnsCreated() throws Exception {
		mockMvc.perform(post("/leaves")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "employeeId": "EMP-001",
						  "leaveType": "CASUAL",
						  "startDate": "2026-12-01",
						  "endDate": "2026-12-03",
						  "reason": "Personal appointment"
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.status").value("PENDING"));
	}

	@Test
	void getMissingLeaveReturnsNotFound() throws Exception {
		mockMvc.perform(get("/leaves/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	void createLeaveReturnsBadRequestForInvalidInput() throws Exception {
		mockMvc.perform(post("/leaves")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "employeeId": "",
						  "leaveType": null,
						  "startDate": null,
						  "endDate": null,
						  "reason": ""
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors.employeeId").exists());
	}

	@Test
	void deleteMissingLeaveReturnsNotFound() throws Exception {
		mockMvc.perform(delete("/leaves/999"))
				.andExpect(status().isNotFound());
	}
}
