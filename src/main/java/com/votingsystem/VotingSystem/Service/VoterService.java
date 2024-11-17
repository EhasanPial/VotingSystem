package com.votingsystem.VotingSystem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votingsystem.VotingSystem.Repository.VoterRepository;
import com.votingsystem.VotingSystem.model.Voter;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    // Get Voter by NID
    public Optional<Voter> getVoterByNid(int nid) {
        return voterRepository.findById(nid);
    }

    // Create or Update Voter
    public Voter saveVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    // Verify Password
    public boolean verifyPassword(String email, String password) {
        Optional<Voter> voter = voterRepository.findByEmail(email);
        return voter.isPresent() && voter.get().getPassword().equals(password);
    }
}
