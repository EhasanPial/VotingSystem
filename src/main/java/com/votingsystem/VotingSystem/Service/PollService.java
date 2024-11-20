package com.votingsystem.VotingSystem.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.votingsystem.VotingSystem.Repository.NotificationRepository;
import com.votingsystem.VotingSystem.Repository.OptionRepository;
import com.votingsystem.VotingSystem.Repository.PollRepository;
import com.votingsystem.VotingSystem.Repository.VoterRepository;
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
	private NotificationRepository notificationRepository;

	@Autowired
	private VoterService adminService;
	
	@Autowired
	private VoterRepository voterRepository;
	
    private final Map<String, PollFactory> factories;

	public PollService() {
       
        factories = new HashMap<>();
        factories.put("OPEN", new OpenPollFactory());
        factories.put("TIME", new TimePollFactory());
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

		// Recalculate percentages
		for (Option opt : poll.getOptions()) {
			int percentage = (int) ((opt.getVoteCount() * 100.0) / poll.getTotalVote());
			opt.setVotePercentage(percentage);
		}
		Optional<Voter> voter =  voterRepository.findByUsername(username);
		if (voter.isPresent()) {
			voter.get().getVotedPolls().add(poll);
			voterRepository.save(voter.get());
			poll.getVoters().add(voter.get());
		}
		Poll updatedPoll = pollRepository.save(poll);
		updatedPoll.notifyVoters("The poll '" + poll.getTitle() + "' has been updated.", notificationRepository, username);
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
