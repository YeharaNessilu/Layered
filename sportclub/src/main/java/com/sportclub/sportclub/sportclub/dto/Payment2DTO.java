package com.sportclub.sportclub.sportclub.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Payment2DTO {
    private String membershipId;
    private String memberId;
    private String status;
    private String fee;
    //private String balance;

}
