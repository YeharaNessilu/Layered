package com.sportclub.sportclub.sportclub.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class MemberDetail {
    private String membershipId;
    private String sportId;
    private String memeberId;
    private String paymentStatus;
    private String JoiningDate;

    public MemberDetail(String memebershipId, String status) {

    }
}
