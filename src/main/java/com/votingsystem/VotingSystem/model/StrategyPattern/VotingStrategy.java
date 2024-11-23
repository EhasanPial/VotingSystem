package com.votingsystem.VotingSystem.model.StrategyPattern;

import com.votingsystem.VotingSystem.model.Poll;

public interface VotingStrategy {
    
    PollResult calculateResults(Poll poll);
}	