package com.votingsystem.VotingSystem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votingsystem.VotingSystem.Repository.OptionRepository;
import com.votingsystem.VotingSystem.Repository.PollRepository;
import com.votingsystem.VotingSystem.model.Constants;
import com.votingsystem.VotingSystem.model.Option;
import com.votingsystem.VotingSystem.model.Poll;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;
    
    @Autowired
    private AdminService adminService;

    public void createPollWithOptions(Poll poll, List<String> optionTitles) {
    	
    	 poll.setAdmin(adminService.getAdminByEmail(Constants.ADMIN_TYPE_1_EMAIL));
         Poll savedPoll = pollRepository.save(poll);

         for (String title : optionTitles) {
            Option option = new Option();
            option.setTitle(title);
            option.setVoteCount(0);
            option.setVotePercentage(0);
            option.setPoll(savedPoll);
            optionRepository.save(option);
         }
    }
}
