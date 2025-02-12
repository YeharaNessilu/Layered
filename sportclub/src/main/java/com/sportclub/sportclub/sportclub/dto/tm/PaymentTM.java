package com.sportclub.sportclub.sportclub.dto.tm;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentTM {
    private String payId;
    //private String membershipId;
    private String memberId;
    private String paymentDate;
    //private String fee;
    private String amount;
    //private String balance;
    //private String status;
}
