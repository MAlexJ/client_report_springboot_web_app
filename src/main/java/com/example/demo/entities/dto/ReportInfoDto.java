package com.example.demo.entities.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ReportInfoDto {

    private List<ReportBuilder> activeBuyers;

    private List<ReportBuilder> inactiveBuyers;

    private List<String> reportMonths;
}