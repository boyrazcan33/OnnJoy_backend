package com.onnjoy.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Therapist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String language; // 'en', 'et', 'lt', 'lv', 'ru'

    @Column(length = 2500)
    private String bio;
}
