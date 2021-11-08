package com.example.demo.services;

import com.example.demo.entities.dto.ClientDto;
import com.example.demo.entities.dto.ClientInfoDto;
import com.example.demo.entities.dto.ReportDto;
import com.example.demo.entities.dto.ReportInfoDto;

public interface AppService {

    ClientInfoDto getClients();

    ClientInfoDto getClientsByLastOrFirstName(String firstName, String lastName);

    ClientInfoDto createClient(ClientDto clientDto);

    void createReport(ReportDto reportDto);

    ReportInfoDto findReports();
}