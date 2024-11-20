package com.votingsystem.VotingSystem.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollRequest {
	private String title;
	private String description;
	private List<Option> options;
	private String pollDate;
	private Category category;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean isActive;
	private Voter admin;
	private String type;
	

}