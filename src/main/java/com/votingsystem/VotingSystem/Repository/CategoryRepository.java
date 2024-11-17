package com.votingsystem.VotingSystem.Repository;

import com.votingsystem.VotingSystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
