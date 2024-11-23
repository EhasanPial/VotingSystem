package com.votingsystem.VotingSystem.StrategyPattern;

import com.votingsystem.VotingSystem.model.Poll;

public interface VotingStrategy {
    
    PollResult calculateResults(Poll poll);
}	