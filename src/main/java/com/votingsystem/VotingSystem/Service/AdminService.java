package com.votingsystem.VotingSystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votingsystem.VotingSystem.Repository.AdminRepository;
import com.votingsystem.VotingSystem.model.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Get Admin by ID
    public Optional<Admin> getAdminById(int adminId) {
        return adminRepository.findById(adminId);
    }

    // Create Admin
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Verify Password
    public boolean verifyPassword(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }
    
    // find by email
	public Admin getAdminByUsername(String username) {
		return adminRepository.findByUsername(username).get();
	}
}
