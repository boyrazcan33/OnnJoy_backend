package com.onnjoy.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String passwordHash;

    private String anonUsername;

    private String languagePreference;

    private Timestamp createdAt;

    private Timestamp lastEntryAt;

    @Column(length = 512)
    private String jwtToken;

    private String timezone;

    private String ipAddress;
}
