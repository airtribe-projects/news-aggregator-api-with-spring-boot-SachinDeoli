package com.airtribe.NewsAggregator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDTO {
    private String category;
    @NotBlank
    @Size(max = 2)
    private String country;
    private String topic;
}
