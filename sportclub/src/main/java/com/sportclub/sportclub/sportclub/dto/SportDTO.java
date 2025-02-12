package com.sportclub.sportclub.sportclub.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SportDTO {
    private String sportId;
    private String name;
    private String payment;
    private String coachId;
}
