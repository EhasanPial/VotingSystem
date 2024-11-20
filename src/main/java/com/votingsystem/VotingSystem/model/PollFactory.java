package com.votingsystem.VotingSystem.model;


public interface PollFactory {
    Poll createPoll(PollRequest request);
}