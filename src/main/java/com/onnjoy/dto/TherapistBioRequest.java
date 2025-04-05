package com.onnjoy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapistBioRequest {
    private Long id;
    private Long therapistId;
    private String language;
    private String bio;

}