package com.votingsystem.VotingSystem.Repository;

import com.votingsystem.VotingSystem.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer> {
    Optional<Voter> findByEmail(String email);
    Optional<Voter> findByUsername(String username);
 }
