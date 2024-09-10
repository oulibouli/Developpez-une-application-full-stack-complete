package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class PostDTOCreate {

    private String title;
    private String content;
    private LocalDateTime date;

}
