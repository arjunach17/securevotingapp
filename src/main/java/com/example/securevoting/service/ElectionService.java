package com.example.securevoting.service;

import com.example.securevoting.dto.CandidateRequest;
import com.example.securevoting.dto.ElectionRequest;
import com.example.securevoting.entity.Candidate;
import com.example.securevoting.entity.Election;

import java.util.List;
import java.util.Map;

public interface ElectionService {

    Election createElection(ElectionRequest request);

    Candidate addCandidate(CandidateRequest request);

    List<Election> getActiveElections();

    Map<String, Long> getElectionResults(Long electionId);
}
