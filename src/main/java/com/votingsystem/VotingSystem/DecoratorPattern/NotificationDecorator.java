package com.votingsystem.VotingSystem.DecoratorPattern;

import java.util.List;

import com.votingsystem.VotingSystem.Repository.NotificationRepository;
import com.votingsystem.VotingSystem.model.Notification;
import com.votingsystem.VotingSystem.model.Voter;

public class NotificationDecorator extends BasePollDecorator {

	private NotificationRepository notificationRepository;
 
	public NotificationDecorator(IPollDecorator pollDecorator, NotificationRepository notificationRepository) {
		super(pollDecorator);
		this.notificationRepository = notificationRepository;
	}

	@Override
	public void performOperation(String message, String username, List<Voter> voters) {
		notifyVoters(message, username, voters);
	}

	private void notifyVoters(String message, String username, List<Voter> subscribedVoters) {
		for (Voter voter : subscribedVoters) {
			if (voter.getUsername().equals(username)) {
				continue;
			}
			Notification notification = new Notification(message, voter, poll,
					Notification.NotificationType.POLL_UPDATED);
			notificationRepository.save(notification);
		}
	}
}
