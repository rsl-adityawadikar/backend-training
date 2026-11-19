package com.example.leave_management.controller;

import com.example.leave_management.dto.LeaveRequestDto;
import com.example.leave_management.dto.LeaveResponseDto;
import com.example.leave_management.dto.LeaveUpdateDto;
import com.example.leave_management.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
	private final LeaveService service;

	public LeaveController(LeaveService service) {
		this.service = service;
	}

	@GetMapping
	public List<LeaveResponseDto> getAllLeaves() {
		return service.getAllLeaves();
	}

	@GetMapping("/{id}")
	public LeaveResponseDto getLeaveById(@PathVariable Long id) {
		return service.getLeaveById(id);
	}

	@PostMapping
	public ResponseEntity<LeaveResponseDto> createLeave(@Valid @RequestBody LeaveRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createLeave(request));
	}

	@PutMapping("/{id}")
	public LeaveResponseDto updateLeave(@PathVariable Long id, @Valid @RequestBody LeaveUpdateDto request) {
		return service.updateLeave(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
		service.deleteLeave(id);
		return ResponseEntity.noContent().build();
	}
}
