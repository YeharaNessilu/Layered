package com.sportclub.sportclub.sportclub.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Sport {
    private String sportId;
    private String name;
    private String payment;
    private String coachId;
}
