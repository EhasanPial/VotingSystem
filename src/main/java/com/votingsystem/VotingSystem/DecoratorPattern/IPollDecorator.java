package com.votingsystem.VotingSystem.DecoratorPattern;

import java.util.List;

import com.votingsystem.VotingSystem.model.Poll;
import com.votingsystem.VotingSystem.model.Voter;

public interface IPollDecorator {

	void performOperation(String message, String username, List<Voter> voters);

	Poll getPoll();
}
