package com.example.securevoting.service;

import com.example.securevoting.dto.VoteRequest;
import com.example.securevoting.entity.Vote;

public interface VoteService {

    Vote castVote(String username, VoteRequest request);
}
