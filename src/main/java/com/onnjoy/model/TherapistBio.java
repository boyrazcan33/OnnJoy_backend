package com.onnjoy.model;

import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name = "therapist_bios")
public class TherapistBio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "therapist_id")
    private Long therapistId;

    @Column(name = "language_code")
    private String language;

    @Column(length = 5000)
    private String bio;

}