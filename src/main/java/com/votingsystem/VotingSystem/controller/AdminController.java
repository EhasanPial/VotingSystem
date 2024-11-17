package com.votingsystem.VotingSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.votingsystem.VotingSystem.Service.AdminService;
import com.votingsystem.VotingSystem.Service.CategoryService;
import com.votingsystem.VotingSystem.Service.PollService;
import com.votingsystem.VotingSystem.model.Admin;
import com.votingsystem.VotingSystem.model.Poll;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private PollService pollService;

//     @GetMapping("/{id}")
//    public ResponseEntity<Admin> getAdminById(@PathVariable("id") int adminId) {
//        Optional<Admin> admin = adminService.getAdminById(adminId);
//        if (admin.isPresent()) {
//            return ResponseEntity.ok(admin.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//     @PostMapping
//    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
//        try {
//            Admin savedAdmin = adminService.createAdmin(admin);
//            return ResponseEntity.ok(savedAdmin);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    // Password Verification Method
//    @PostMapping("/verify-password")
//    public ResponseEntity<String> verifyPassword(@RequestParam String email, @RequestParam String password) {
//        boolean isVerified = adminService.verifyPassword(email, password);
//        if (isVerified) {
//            return ResponseEntity.ok("Password is valid.");
//        } else {
//            return ResponseEntity.status(401).body("Invalid email or password.");
//        }
//    }
    
    // ---------------- Front End ---------------- //
    
    
   
}
