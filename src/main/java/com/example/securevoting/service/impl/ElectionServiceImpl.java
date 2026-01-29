package com.example.securevoting.service.impl;

import com.example.securevoting.dto.CandidateRequest;
import com.example.securevoting.dto.ElectionRequest;
import com.example.securevoting.entity.Candidate;
import com.example.securevoting.entity.Election;
import com.example.securevoting.entity.Vote;
import com.example.securevoting.exception.BadRequestException;
import com.example.securevoting.exception.ResourceNotFoundException;
import com.example.securevoting.repository.CandidateRepository;
import com.example.securevoting.repository.ElectionRepository;
import com.example.securevoting.repository.VoteRepository;
import com.example.securevoting.service.ElectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository,
                               CandidateRepository candidateRepository,
                               VoteRepository voteRepository) {
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    @Transactional
    public Election createElection(ElectionRequest request) {
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new BadRequestException("End time must be after start time");
        }

        Election election = new Election();
        election.setTitle(request.getTitle());
        election.setDescription(request.getDescription());
        election.setStartTime(request.getStartTime());
        election.setEndTime(request.getEndTime());

        return electionRepository.save(election);
    }

    @Override
    @Transactional
    public Candidate addCandidate(CandidateRequest request) {
        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

        Candidate candidate = new Candidate();
        candidate.setName(request.getName());
        candidate.setDescription(request.getDescription());
        candidate.setElection(election);

        return candidateRepository.save(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Election> getActiveElections() {
        LocalDateTime now = LocalDateTime.now();
        return electionRepository.findByStartTimeBeforeAndEndTimeAfter(now, now);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getElectionResults(Long electionId) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

        List<Candidate> candidates = candidateRepository.findByElectionId(electionId);

        Map<String, Long> result = new LinkedHashMap<>();
        for (Candidate c : candidates) {
            long count = voteRepository.countByElectionAndCandidate(election, c);
            result.put(c.getName(), count);
        }
        return result;
    }
}
