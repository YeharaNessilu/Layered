package com.sportclub.sportclub.sportclub.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class MemberDetailDTO {
    private String membershipId;
    private String sportId;
    private String memeberId;
    private String paymentStatus;
    private String JoiningDate;
}
