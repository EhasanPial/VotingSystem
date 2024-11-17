package com.votingsystem.VotingSystem.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.votingsystem.VotingSystem.Service.VoterService;
import com.votingsystem.VotingSystem.model.Voter;

@RestController
@RequestMapping("/voter")
public class VoterController {

    @Autowired
    private VoterService voterService;

    // GET method: Retrieve a voter by NID
    @GetMapping("/{nid}")
    public ResponseEntity<Voter> getVoterByNid(@PathVariable("nid") int nid) {
        Optional<Voter> voter = voterService.getVoterByNid(nid);
        if (voter.isPresent()) {
            return ResponseEntity.ok(voter.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST method: Create or update a voter
    @PostMapping
    public ResponseEntity<Voter> saveVoter(@RequestBody Voter voter) {
        try {
            Voter savedVoter = voterService.saveVoter(voter);
            return ResponseEntity.ok(savedVoter);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    
    
    // --------------------- Frontend --------------------- //
    
}
