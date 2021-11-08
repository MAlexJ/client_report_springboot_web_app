package com.example.demo.entities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class ClientDto {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String phone;

    @NonNull
    private LocalDate date;

    @JsonCreator
    public ClientDto(@JsonProperty("firstName") String firstName,
                     @JsonProperty("lastName") String lastName,
                     @JsonProperty("phone") String phone,
                     @JsonProperty("date") LocalDate date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.date = date;
    }

}