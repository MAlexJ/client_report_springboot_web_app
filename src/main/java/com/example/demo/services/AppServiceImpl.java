package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.Report;
import com.example.demo.entities.dto.*;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ReportRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    private final ClientRepository clientRepository;

    private final ReportRepository reportRepository;

    private final ModelMapper modelMapper;

    public AppServiceImpl(ClientRepository clientRepository,
                          ReportRepository reportRepository,
                          ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientInfoDto getClients() {
        List<Client> clients = clientRepository.findAll();
        return ClientInfoDto.builder()
                .clients(clients)
                .total(clients.size())
                .build();
    }

    @Override
    public ClientInfoDto createClient(ClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        clientRepository.save(client);
        List<Client> clients = clientRepository.findAll();
        return ClientInfoDto.builder()
                .clients(clients)
                .total(clients.size())
                .build();
    }

    @Override
    public void createReport(ReportDto reportDto) {
        Report report = modelMapper.map(reportDto, Report.class);
        Integer clientId = report.getClientId();
        LocalDate newDate = report.getDate().plusDays(1);
        report.setDate(newDate);
        Client client = clientRepository.findClientById(clientId);
        report.setClient(client);
        reportRepository.save(report);
    }


    @Override
    public ClientInfoDto getClientsByLastOrFirstName(String firstName, String lastName) {
        firstName = Objects.nonNull(firstName) ? firstName : "";
        lastName = Objects.nonNull(lastName) ? lastName : "";
        List<Client> clients = clientRepository.findClientsByFirstNameContainsAndLastNameContains(firstName, lastName);
        return ClientInfoDto.builder()
                .clients(clients)
                .total(clients.size())
                .build();
    }

    @Override
    public ReportInfoDto findReports() {
        List<ReportBuilder> reportBuilderList = convertObjectsToReportList(reportRepository.findAllReportsGroupByMonth());
        Map<Boolean, List<ReportBuilder>> reportGroups = groupReportsByAugust(reportBuilderList);
        List<String> months = convertNumbersToMonthNames(reportRepository.findAllReportMonth());
        return ReportInfoDto.builder()
                .activeBuyers(reportGroups.get(true))
                .inactiveBuyers(reportGroups.get(false))
                .reportMonths(months)
                .build();
    }

    private Map<Boolean, List<ReportBuilder>> groupReportsByAugust(List<ReportBuilder> reports) {
        return reports.stream()
                .collect(Collectors.groupingBy(s -> Objects.nonNull(s.getAugust())));
    }

    List<String> convertNumbersToMonthNames(List<?> monthNumbers) {
        return monthNumbers.stream()
                .filter(Objects::nonNull)
                .map(this::parseMonth)
                .collect(Collectors.toList());
    }

    String parseMonth(Object ob) {
        int month;
        if (ob instanceof Integer) {
            month = (Integer) ob;
        } else if (ob instanceof BigInteger) {
            month = ((BigInteger) ob).intValue();
        } else {
            throw new IllegalArgumentException("Incorrect month number format!");
        }
        return Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
    }


    private List<ReportBuilder> convertObjectsToReportList(List<?> reports) {
        return reports.stream()
                .filter(Objects::nonNull)
                .filter(Object[].class::isInstance)
                .map(Object[].class::cast)
                .map(this::populateReportBuilder)
                .collect(Collectors.toList());
    }

    ReportBuilder populateReportBuilder(Object[] arr) {
        return ReportBuilder.builder()
                .fullName(populateFullName(arr[0]))
                .january(populatePrice(arr[1]))
                .february(populatePrice(arr[2]))
                .march(populatePrice(arr[3]))
                .april(populatePrice(arr[4]))
                .may(populatePrice(arr[5]))
                .june(populatePrice(arr[6]))
                .july(populatePrice(arr[7]))
                .august(populatePrice(arr[8]))
                .september(populatePrice(arr[9]))
                .october(populatePrice(arr[10]))
                .november(populatePrice(arr[11]))
                .december(populatePrice(arr[12]))
                .phone(populatePhone(arr[13]))
                .build();
    }

    String populatePrice(Object value) {
        return Optional.ofNullable(value)
                .map(BigDecimal.class::cast)
                .map(BigDecimal::toPlainString)
                .orElse(null);
    }

    String populateFullName(Object value) {
        return Optional.ofNullable(value)
                .map(String.class::cast)
                .filter(StringUtils::isNoneBlank)
                .orElseThrow(() -> new IllegalArgumentException("Client full name is required!"));
    }

    String populatePhone(Object value) {
        return Optional.ofNullable(value)
                .map(String.class::cast)
                .orElse(null);
    }
}