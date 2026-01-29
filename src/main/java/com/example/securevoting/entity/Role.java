package com.example.securevoting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g. "ROLE_ADMIN", "ROLE_USER"
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
