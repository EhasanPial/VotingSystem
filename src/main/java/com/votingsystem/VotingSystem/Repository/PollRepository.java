package com.votingsystem.VotingSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votingsystem.VotingSystem.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Integer> {

	Optional<Poll> findByPollId(Long pollId);
}
