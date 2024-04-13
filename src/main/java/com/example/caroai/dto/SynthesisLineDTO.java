package com.example.caroai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SynthesisLineDTO {
    private Long id;
    private Long synthesisId;
    private Boolean isWin;
    private Integer yourNumberOfMoves;
    private Integer botNumberMoves;
    private Integer level;
    private Boolean youFirst;
}
