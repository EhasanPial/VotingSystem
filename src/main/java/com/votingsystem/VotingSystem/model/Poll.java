package com.votingsystem.VotingSystem.model;

import java.util.ArrayList;
import java.util.List;

import com.votingsystem.VotingSystem.StrategyPattern.PollResult;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "poll")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Poll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PollID")
	private int pollId;

	@Column(name = "Title", nullable = false)
	private String title;


	@Column(name = "PollDate")
	private String pollDate;

	@Column(name = "TotalVote")
	private int totalVote;

	@ManyToOne
	@JoinColumn(name = "AdminID")
	private Voter admin;

	@ManyToOne
	@JoinColumn(name = "CategoryID", nullable = false)
	private Category category;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
	private List<Option> options;

	@ManyToMany(mappedBy = "votedPolls")
	private List<Voter> voters;

	@ManyToMany
	@JoinTable(name = "poll_voter_subscription", joinColumns = @JoinColumn(name = "poll_id"), inverseJoinColumns = @JoinColumn(name = "NID"))
	private List<Voter> subscribedVoters = new ArrayList<>();
	
	@Column(name = "voting_strategy")
	private String votingStrategy;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "poll_result_id", referencedColumnName = "id")
    private PollResult pollResults;

 	public void subscribe(Voter voter) {
		subscribedVoters.add(voter);
	}

 	public void unsubscribe(Voter voter) {
		subscribedVoters.remove(voter);
	}

	 

}
