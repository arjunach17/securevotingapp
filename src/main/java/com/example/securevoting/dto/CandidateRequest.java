// dto/CandidateRequest.java
package com.example.securevoting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CandidateRequest {

    @NotNull
    private Long electionId;

    @NotBlank
    private String name;

    private String description;
}
