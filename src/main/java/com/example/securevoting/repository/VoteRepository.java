package com.example.securevoting.repository;

import com.example.securevoting.entity.Election;
import com.example.securevoting.entity.User;
import com.example.securevoting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndElection(User user, Election election);

    long countByElectionAndCandidate(Election election, com.example.securevoting.entity.Candidate candidate);

    List<Vote> findByElection(Election election);
}
