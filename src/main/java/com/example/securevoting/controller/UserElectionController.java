package com.example.securevoting.controller;

import com.example.securevoting.dto.VoteRequest;
import com.example.securevoting.entity.Election;
import com.example.securevoting.entity.Vote;
import com.example.securevoting.service.ElectionService;
import com.example.securevoting.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/elections")
public class UserElectionController {

    private final ElectionService electionService;
    private final VoteService voteService;

    public UserElectionController(ElectionService electionService,
                                  VoteService voteService) {
        this.electionService = electionService;
        this.voteService = voteService;
    }

    // View active elections
    @GetMapping("/active")
    public ResponseEntity<List<Election>> getActiveElections() {
        return ResponseEntity.ok(electionService.getActiveElections());
    }

    // Vote in an election (authenticated user)
    @PostMapping("/vote")
    public ResponseEntity<Vote> castVote(
            @AuthenticationPrincipal UserDetails currentUser,
            @Valid @RequestBody VoteRequest request) {

        String username = currentUser.getUsername();
        Vote vote = voteService.castVote(username, request);
        return ResponseEntity.ok(vote);
    }
}
