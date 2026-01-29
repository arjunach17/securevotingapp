// dto/VoteRequest.java
package com.example.securevoting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VoteRequest {

    @NotNull
    private Long electionId;

    @NotNull
    private Long candidateId;
}
