package com.example.demo.entities.dto;

import com.example.demo.entities.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ClientInfoDto {

    private List<Client> clients;

    private int total;
}