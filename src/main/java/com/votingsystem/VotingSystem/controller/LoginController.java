package com.votingsystem.VotingSystem.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.votingsystem.VotingSystem.Service.VoterService;
import com.votingsystem.VotingSystem.model.Voter;

@Controller
public class LoginController {
	@Autowired
	private VoterService voterService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/register")
	public ModelAndView showRegistrationForm(@ModelAttribute Voter model) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("voter-registration");
		modelAndView.addObject("voter", new Voter());
		return modelAndView;
	}

	@PostMapping("/register")
	public String registerVoter(@ModelAttribute Voter voter, Model model) {
		if (voterService.findByEmail(voter.getEmail()).isPresent()) {
			model.addAttribute("error", "Email is already registered.");
			return "register";
		}

		// Encode the password
		voter.setPassword(passwordEncoder.encode(voter.getPassword()));

		// Save voter
		voter.setEnabled(true); // Enable the user by default
		voterService.saveVoter(voter);

		return "redirect:/login?registered=true"; // Redirect to login with a success flag
	}

	@GetMapping("/index")
	public String showIndexPage(Principal principal) {
	    String username = principal.getName(); // Gets logged in user's email
	    
	    if ("admin".equals(username)) {
	        return "redirect:/admin-index";
	    }
	    return "redirect:/polls";
	}


}