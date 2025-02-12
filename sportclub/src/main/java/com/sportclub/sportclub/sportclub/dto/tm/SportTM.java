package com.sportclub.sportclub.sportclub.dto.tm;

import com.sportclub.sportclub.sportclub.dto.SportDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SportTM extends SportDTO {
    private String sportId;
    private String name;
    private String payment;
    private String coachId;

}
