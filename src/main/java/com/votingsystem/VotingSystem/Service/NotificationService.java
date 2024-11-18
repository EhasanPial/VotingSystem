package com.votingsystem.VotingSystem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votingsystem.VotingSystem.Repository.NotificationRepository;
import com.votingsystem.VotingSystem.Repository.VoterRepository;
import com.votingsystem.VotingSystem.model.Notification;
import com.votingsystem.VotingSystem.model.Voter;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private VoterRepository voterRepository;

	public void markAsRead(Long notificationId, String username) {
		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		if (!notification.getRecipient().getUsername().equals(username)) {
			throw new SecurityException("Unauthorized access to this notification.");
		}

		notification.setRead(true);
		notificationRepository.save(notification);
	}

	public List<Notification> getAllNotifications(Voter voter) {

		return notificationRepository.findByRecipient(voter);

	}

}
