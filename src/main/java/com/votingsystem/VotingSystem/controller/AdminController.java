package com.votingsystem.VotingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.votingsystem.VotingSystem.Service.CategoryService;
import com.votingsystem.VotingSystem.Service.PollService;
import com.votingsystem.VotingSystem.model.Category;
import com.votingsystem.VotingSystem.model.PollRequest;

@Controller
public class AdminController {

    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private PollService pollService;

   

	@GetMapping("/admin-index")
	public String showAdminIndexPage(Model model) {
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "admin-index";
	}

	@GetMapping("/admin-create-poll")
	public String showPollCreationForm(Model model) {
		model.addAttribute("poll", new PollRequest());
		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);
		return "poll-create";
	}

	@PostMapping("/admin-create-poll")
	public String createPoll(@ModelAttribute PollRequest poll, @RequestParam List<String> optionTitles,
			RedirectAttributes redirectAttributes) {
		try {
			pollService.createPollWithOptions(poll, optionTitles, poll.getType());
			redirectAttributes.addFlashAttribute("message", "Poll created successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while creating the poll.");
			redirectAttributes.addFlashAttribute("message", "Failed to create poll!");
		}
		return "redirect:/admin-create-poll";
	}
    
   
}
