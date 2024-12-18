package com.votingsystem.VotingSystem.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.votingsystem.VotingSystem.DecoratorPattern.BasePollDecorator;
import com.votingsystem.VotingSystem.DecoratorPattern.IPollDecorator;
import com.votingsystem.VotingSystem.DecoratorPattern.NotificationDecorator;
import com.votingsystem.VotingSystem.Repository.NotificationRepository;
import com.votingsystem.VotingSystem.Repository.OptionRepository;
import com.votingsystem.VotingSystem.Repository.PollRepository;
import com.votingsystem.VotingSystem.Repository.VoterRepository;
import com.votingsystem.VotingSystem.StrategyPattern.FirstPastThePostStrategy;
import com.votingsystem.VotingSystem.StrategyPattern.PollResult;
import com.votingsystem.VotingSystem.StrategyPattern.VotingStrategy;
import com.votingsystem.VotingSystem.StrategyPattern.WeightedVotingStrategy;
import com.votingsystem.VotingSystem.model.Constants;
import com.votingsystem.VotingSystem.model.OpenPollFactory;
import com.votingsystem.VotingSystem.model.Option;
import com.votingsystem.VotingSystem.model.Poll;
import com.votingsystem.VotingSystem.model.PollFactory;
import com.votingsystem.VotingSystem.model.PollRequest;
import com.votingsystem.VotingSystem.model.TimePollFactory;
import com.votingsystem.VotingSystem.model.Voter;

@Service
public class PollService {

	@Autowired
	private PollRepository pollRepository;

	@Autowired
	private OptionRepository optionRepository;
 
	@Autowired
	private VoterService adminService;

	@Autowired
	private VoterRepository voterRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;

	private final Map<String, PollFactory> factories;
	private final Map<String, VotingStrategy> votingStrategies;

	public PollService() {

		factories = new HashMap<>();
		factories.put("OPEN", new OpenPollFactory());
		factories.put("TIME", new TimePollFactory());

		votingStrategies = new HashMap<>();
		votingStrategies.put(Constants.TRADITIONAL_METHOD, new FirstPastThePostStrategy());
		votingStrategies.put(Constants.WEIGHTED_METHOD, new WeightedVotingStrategy(null));
	}

	public List<Poll> getAllPolls() {
		return pollRepository.findAll();
	}

	public void createPollWithOptions(PollRequest poll, List<String> optionTitles, String type) {

		Optional<Voter> adminUser = adminService.getVoterByUsername(Constants.ADMIN_TYPE_1_USER_NAME);

		if (adminUser.isPresent()) {
			poll.setAdmin(adminUser.get());
		}

		PollFactory factory = factories.get(type);
		Poll newPoll = factory.createPoll(poll);
		Poll savedPoll = pollRepository.save(newPoll);

		for (String title : optionTitles) {
			Option option = new Option();
			option.setTitle(title);
			option.setVoteCount(0);
			option.setVotePercentage(0);
			option.setPoll(savedPoll);
			optionRepository.save(option);
		}
	}

	public void castVote(int optionId, String username) {
		Option option = optionRepository.findById(optionId).orElseThrow(() -> new RuntimeException("Option not found"));

		// Increment vote count
		option.setVoteCount(option.getVoteCount() + 1);

		// Update poll's total vote count
		Poll poll = option.getPoll();
		poll.setTotalVote(poll.getTotalVote() + 1);

		// Recalculate percentages based on voting strategy
		VotingStrategy votingStrategy = votingStrategies.get(poll.getVotingStrategy());
		PollResult result = votingStrategy.calculateResults(poll);

		// check poll result is already save or not in db
		if (poll.getPollResults() == null) {
			result.setTotalVotes(poll.getTotalVote()); // Ensure totalVotes is set
			poll.setPollResults(result);
			result.setPoll(poll);
		} else {
			PollResult existingResult = poll.getPollResults();
			existingResult.setTotalVotes(poll.getTotalVote());
			existingResult.setVoteCounts(result.getVoteCounts());
			existingResult.setVotePercentages(result.getVotePercentages());
			existingResult.setWinner(result.getWinner());
		}
		result.setPoll(poll);
		poll = pollRepository.save(poll);

		Optional<Voter> voter = voterRepository.findByUsername(username);
		if (voter.isPresent()) {
			List<Poll> voterPolls = voter.get().getVotedPolls();
			if (voterPolls == null) {
				voterPolls = new ArrayList<>();
			}
			voterPolls.add(poll);
			voterRepository.save(voter.get());
			poll.getVoters().add(voter.get());
		}
		Poll updatedPoll = pollRepository.save(poll);
		option.setVotePercentage(result.getVotePercentages().get(option.getTitle()));
		optionRepository.save(option);

		// Notify subscribed voters
		sendNotification(updatedPoll, username);

	}

	private void sendNotification(Poll updatedPoll, String username) {
		IPollDecorator pollDecorator = new BasePollDecorator(updatedPoll);
		pollDecorator = new NotificationDecorator(pollDecorator, notificationRepository);

		pollDecorator.performOperation("The poll '" + updatedPoll.getTitle() + "' has been updated.", username,
				updatedPoll.getSubscribedVoters());

	}

	public String subscribeToPoll(Long pollId, String username) {
		Voter voter = voterRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter not found"));
		Poll poll = pollRepository.findByPollId(pollId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll not found"));

		if (!poll.getSubscribedVoters().contains(voter)) {
			poll.subscribe(voter);
			pollRepository.save(poll);
			voter.addSubscribedPoll(poll);
			voterRepository.save(voter);
			return "Subscribed successfully to the poll.";
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "Already subscribed to this poll.");
	}

	public String unsubscribeFromPoll(Long pollId, String username) {
		Voter voter = voterRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Voter not found"));
		Poll poll = pollRepository.findByPollId(pollId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poll not found"));

		if (poll.getSubscribedVoters().contains(voter)) {
			poll.unsubscribe(voter);
			pollRepository.save(poll);
			voter.removeSubscribedPoll(poll);
			voterRepository.save(voter);
			return "Unsubscribed successfully from the poll.";
		}

		throw new ResponseStatusException(HttpStatus.CONFLICT, "You are not subscribed to this poll.");
	}

}
