package com.votingsystem.VotingSystem.DecoratorPattern;

import java.util.List;

import com.votingsystem.VotingSystem.model.Poll;
import com.votingsystem.VotingSystem.model.Voter;

 

public class BasePollDecorator implements IPollDecorator {

    protected IPollDecorator decoratedPoll;
    protected Poll poll;
    
    public BasePollDecorator(Poll poll) {
        this.poll = poll;
    }

    public BasePollDecorator(IPollDecorator decoratedPoll) {
        this.poll = decoratedPoll.getPoll();
        this.decoratedPoll = decoratedPoll;
    }

    @Override
    public void performOperation(String message, String username, List<Voter> voters) {
         System.out.println("Default poll operation");
    }

	@Override
	public Poll getPoll() {
		return this.poll;
	}
}
