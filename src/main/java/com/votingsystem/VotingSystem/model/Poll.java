package com.votingsystem.VotingSystem.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "poll")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PollID")
	private int pollId;

	@Column(name = "Title", nullable = false)
	private String title;

	@Column(name = "type")
	private String type;

	@Column(name = "PollDate")
	private String pollDate;

	@Column(name = "TotalVote")
	private int totalVote;

	@ManyToOne
	@JoinColumn(name = "AdminID", nullable = false)
	private Admin admin;

	@ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category category;

	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
	private List<Option> options;

}
