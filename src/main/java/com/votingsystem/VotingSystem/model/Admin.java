package com.votingsystem.VotingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// test
@Entity
@Table(name = "admin")
@Data  
@NoArgsConstructor 
@AllArgsConstructor 
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdminID")
    private int adminId;

    @Column(name = "Username", nullable = false, length = 32)
    private String username;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "type")
    private int type; // 1 - Voter Verifier // 2 - Poll Manager
}
