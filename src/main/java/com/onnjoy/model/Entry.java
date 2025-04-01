package com.onnjoy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "language_code")
    private String language;

    @Column(name = "content", length = 1000)
    private String text;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
