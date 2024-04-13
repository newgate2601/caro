package com.example.caroai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SynthesisLineDTOList {
    private List<SynthesisLineDTO> synthesisLineDTOs;
}
