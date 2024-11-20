package com.votingsystem.VotingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("OPEN")
@Setter
@Getter
public class OpenPoll extends Poll {
    @Column(name = "is_active")
    private boolean isActive = true;

    // Constructor, getters, setters
}