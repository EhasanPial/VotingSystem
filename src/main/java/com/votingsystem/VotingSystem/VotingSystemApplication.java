package com.votingsystem.VotingSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.votingsystem.VotingSystem.Repository.VoterRepository;
import com.votingsystem.VotingSystem.model.Voter;

@SpringBootApplication
public class VotingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(VoterRepository voterRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (voterRepository.findByUsername("admin-1").isEmpty()) {

				Voter admin = new Voter();
				admin.setUsername("admin-1");
				admin.setNid(-1);
				admin.setEmail("admin_1@example.com");
				admin.setEnabled(true);
				admin.setPassword(passwordEncoder.encode("1234"));

				voterRepository.save(admin);

			}
			if (voterRepository.findByUsername("admin-2").isEmpty()) {

				Voter admin = new Voter();
				admin.setUsername("admin-2");
				admin.setNid(-2);
				admin.setEmail("admin_2@example.com");
				admin.setEnabled(true);
				admin.setPassword(passwordEncoder.encode("1234"));

				voterRepository.save(admin);
			}
		};
	}

}
