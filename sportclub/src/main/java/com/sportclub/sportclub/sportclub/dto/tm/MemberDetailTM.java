package com.sportclub.sportclub.sportclub.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class MemberDetailTM {
    private String membershipId;
    private String sportId;
    private String memberId;
    private String paymentStatus;
    private String JoiningDate;
}
