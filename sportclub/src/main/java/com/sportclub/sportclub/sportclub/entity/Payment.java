package com.sportclub.sportclub.sportclub.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Payment {
    private String payId;
    //private String membershipId;
    private String memberId;
    private String paymentDate;
   // private String fee;
    private String amount;


    // private String balance;
    //private String status;
}
