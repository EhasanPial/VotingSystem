package com.votingsystem.VotingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votingsystem.VotingSystem.model.Notification;
import com.votingsystem.VotingSystem.model.Voter;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(Voter voter);
}