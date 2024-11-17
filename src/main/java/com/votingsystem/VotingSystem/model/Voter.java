package com.votingsystem.VotingSystem.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "Username", nullable = false, length = 32)
    private String username;

    @Column(name = "Address", length = 100)
    private String address;

    @Column(name = "Phone", length = 11)
    private String phone;

    @Column(name = "Email", unique = true) // Ensure email is unique
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
    
    private String role = "ROLE_USER";
    
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "nid"))
//    @Column(name = "role")
//    private Set<String> roles = new HashSet<>();
}
