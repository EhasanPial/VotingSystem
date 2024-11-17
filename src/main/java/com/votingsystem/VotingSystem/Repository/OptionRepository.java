package com.votingsystem.VotingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votingsystem.VotingSystem.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {
}
