package com.votingsystem.VotingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.votingsystem.VotingSystem.Service.AdminService;
import com.votingsystem.VotingSystem.Service.CategoryService;
import com.votingsystem.VotingSystem.Service.PollService;
import com.votingsystem.VotingSystem.Service.VoterService;
import com.votingsystem.VotingSystem.model.Category;
import com.votingsystem.VotingSystem.model.Poll;

@Controller
public class AuthController {

	@Autowired
	private VoterService voterService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PollService pollService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, Model model) {

		if (email.equalsIgnoreCase("admin-17-11-24@gmail.com")) {
			boolean admin = adminService.verifyPassword(email, password);
			if (admin) {
				return "redirect:/admin-index";
			} else {
				model.addAttribute("error", "Invalid email or password. Please try again.");
				return "login";
			}
		}

		boolean voter = voterService.verifyPassword(email, password);

		if (voter) {
			return "redirect:/index";
		} else {
			model.addAttribute("error", "Invalid email or password. Please try again.");
			return "login";
		}
	}

	@GetMapping("/index")
	public String showIndexPage() {
		return "index";
	}

	@GetMapping("/admin-index")
	public String showAdminIndexPage(Model model) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "admin-index";
	}

	@GetMapping("/admin/create-poll")
	public String showPollCreationForm(Model model) {
 		model.addAttribute("poll", new Poll());
 		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "poll-create";
	}

	@PostMapping("/admin/create-poll")
	public String createPoll(@ModelAttribute Poll poll, 
	                         @RequestParam List<String> optionTitles, 
	                         Model model) {
	    try {
	        pollService.createPollWithOptions(poll, optionTitles);
	        model.addAttribute("message", "Poll created successfully!");
	        return "redirect:/admin/create-poll"; // Redirect after successful creation
	    } catch (Exception e) {
	        model.addAttribute("error", "An error occurred while creating the poll.");
	        return "poll-create"; // Return to form on error
	    }
	}

}
