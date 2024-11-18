package com.votingsystem.VotingSystem.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.votingsystem.VotingSystem.Repository.VoterRepository;
import com.votingsystem.VotingSystem.Service.NotificationService;
import com.votingsystem.VotingSystem.Service.PollService;
import com.votingsystem.VotingSystem.model.Notification;
import com.votingsystem.VotingSystem.model.Poll;
import com.votingsystem.VotingSystem.model.Voter;

@Controller
public class PollController {

	@Autowired
	private PollService pollService;

	@Autowired
	private VoterRepository voterRepository;
	
	@Autowired
	private NotificationService notificationService;

	@GetMapping("/polls")
	public String showPollsForVoting(Model model,  Principal principal) {
		List<Poll> polls = pollService.getAllPolls();
        Optional<Voter> currentUser = voterRepository.findByUsername(principal.getName());
        
        if (currentUser.isPresent()) {
            model.addAttribute("currentUser", currentUser.get());
            List<Notification> notifications = notificationService.getAllNotifications(currentUser.get());
            Collections.sort(notifications, (n1, n2) -> n2.getTimestamp().compareTo(n1.getTimestamp()));
            // unread notifications count
            long unreadCount = notifications.stream().filter(n -> !n.isRead()).count();
            model.addAttribute("unreadcount", unreadCount);
            model.addAttribute("notifications", notifications);
        }
      
		model.addAttribute("polls", polls);
		
  		return "polls-view";
	}

	@PostMapping("/polls/vote")
	public String castVote(Principal principal, @RequestParam("optionId") int optionId, Model model) {

		String username = principal.getName();

		pollService.castVote(optionId, username);
		return "redirect:/polls";
	}

	@PostMapping("/polls/{pollId}/subscribe")
	public ResponseEntity<?> subscribeToPoll(@PathVariable Long pollId, Principal principal) {
		String responseMessage = pollService.subscribeToPoll(pollId, principal.getName());
		return ResponseEntity.ok(responseMessage);
	}

	@PostMapping("/polls/{pollId}/unsubscribe")
	public ResponseEntity<?> unsubscribeFromPoll(@PathVariable Long pollId, Principal principal) {
		String responseMessage = pollService.unsubscribeFromPoll(pollId, principal.getName());
		return ResponseEntity.ok(responseMessage);
	}

}
