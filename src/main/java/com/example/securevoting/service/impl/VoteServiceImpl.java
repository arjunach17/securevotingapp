package com.example.securevoting.service.impl;

import com.example.securevoting.dto.VoteRequest;
import com.example.securevoting.entity.*;
import com.example.securevoting.exception.BadRequestException;
import com.example.securevoting.exception.ResourceNotFoundException;
import com.example.securevoting.repository.*;
import com.example.securevoting.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class VoteServiceImpl implements VoteService {

    private final UserRepository userRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    public VoteServiceImpl(UserRepository userRepository,
                           ElectionRepository electionRepository,
                           CandidateRepository candidateRepository,
                           VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    @Transactional
    public Vote castVote(String username, VoteRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

        if (!isElectionActive(election)) {
            throw new BadRequestException("Election is not active");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        if (!candidate.getElection().getId().equals(election.getId())) {
            throw new BadRequestException("Candidate does not belong to this election");
        }

        // Check if user has already voted in this election
        if (voteRepository.findByUserAndElection(user, election).isPresent()) {
            throw new BadRequestException("User has already voted in this election");
        }

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setElection(election);
        vote.setCandidate(candidate);
        vote.setCreatedAt(LocalDateTime.now());

        return voteRepository.save(vote);
    }

    private boolean isElectionActive(Election election) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(election.getStartTime()) && now.isBefore(election.getEndTime());
    }
}
