package com.votingsystem.VotingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "voter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {

    @Id
    @Column(name = "NID", nullable = false)
    private int nid;

    @Column(name = "Name", nullable = false, length = 32)
    private String name;

    @Column(name = "Address", length = 100)
    private String address;

    @Column(name = "Phone", length = 11)
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password", nullable = false, length = 20)
    private String password;

//    @Column(name = "dob")
//    @Temporal(TemporalType.DATE)
//    private Date dob;
}
