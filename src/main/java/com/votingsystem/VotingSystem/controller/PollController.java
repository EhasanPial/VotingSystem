package com.votingsystem.VotingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.votingsystem.VotingSystem.Service.PollService;
import com.votingsystem.VotingSystem.model.Poll;

@Controller
public class PollController {
	
	@Autowired
	private PollService pollService;
	
	@GetMapping("/polls")
	public String showPollsForVoting(Model model) {
	    List<Poll> polls = pollService.getAllPolls();
	    model.addAttribute("polls", polls);
	    return "polls-view";
	}
	
	@PostMapping("/polls/vote")
	public String castVote(@RequestParam("optionId") int optionId, Model model) {
	    pollService.castVote(optionId);
	    return "redirect:/polls"; 
	}
}

