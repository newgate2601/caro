package com.example.caroai.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SynthesisDTO {
    private String userName;
    private Integer winCount;
    private Integer lostCount;
}
