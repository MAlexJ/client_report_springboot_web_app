package com.example.demo.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class ReportDto {

    @NonNull
    private Double price;

    @NonNull
    private LocalDate date;

    @NonNull
    private Integer clientId;

    @JsonCreator
    public ReportDto(@JsonProperty("price") Double price,
                     @JsonProperty("date") LocalDate date,
                     @JsonProperty("clientId") Integer clientId) {
        this.price = price;
        this.date = date;
        this.clientId = clientId;
    }

}