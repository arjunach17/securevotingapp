package com.example.securevoting.controller;

import com.example.securevoting.dto.CandidateRequest;
import com.example.securevoting.dto.ElectionRequest;
import com.example.securevoting.entity.Candidate;
import com.example.securevoting.entity.Election;
import com.example.securevoting.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/elections")
public class AdminElectionController {

    private final ElectionService electionService;

    public AdminElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    // Create an election
    @PostMapping
    public ResponseEntity<Election> createElection(
            @Valid @RequestBody ElectionRequest request) {
        Election election = electionService.createElection(request);
        return ResponseEntity.ok(election);
    }

    // Add candidate to an election
    @PostMapping("/candidates")
    public ResponseEntity<Candidate> addCandidate(
            @Valid @RequestBody CandidateRequest request) {
        Candidate candidate = electionService.addCandidate(request);
        return ResponseEntity.ok(candidate);
    }

    // View results for an election
    @GetMapping("/{electionId}/results")
    public ResponseEntity<?> getResults(@PathVariable Long electionId) {
        return ResponseEntity.ok(electionService.getElectionResults(electionId));
    }
}
