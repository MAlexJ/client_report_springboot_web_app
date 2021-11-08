package com.example.demo.controllers;

import com.example.demo.entities.dto.ClientDto;
import com.example.demo.entities.dto.ClientInfoDto;
import com.example.demo.entities.dto.ReportDto;
import com.example.demo.entities.dto.ReportInfoDto;
import com.example.demo.services.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/v1")
public class RestAppController {

    private final AppService service;

    public RestAppController(AppService service) {
        this.service = service;
    }


    @Operation(summary = "Get all clients or search clients by first or last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get clients",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientInfoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientInfoDto> getClients(
            @Parameter(description = "Client first name") @RequestParam(required = false) String firstName,
            @Parameter(description = "Client last name") @RequestParam(required = false) String lastName) {
        if (StringUtils.isNotBlank(firstName) || StringUtils.isNotBlank(lastName)) {
            return ResponseEntity
                    .ok(service.getClientsByLastOrFirstName(firstName, lastName));
        }
        return ResponseEntity
                .ok(service.getClients());
    }


    @Operation(summary = "Create new client with first and last name, phone and date of birth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successfully create a new client",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ClientInfoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(path = "/clients",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientInfoDto> createClient(@Parameter(description = "Client JSON object representation")
                                                      @RequestBody ClientDto clientDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createClient(clientDto));
    }


    @Operation(summary = "Register new report with client and client data: price, month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Successfully create a new report"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(path = "/reports",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReport(@Parameter(description = "Report JSON object representation")
                                               @RequestBody ReportDto reportDto) {
        service.createReport(reportDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


    @Operation(summary = "Get all reports with clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Get all reports",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportInfoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(path = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportInfoDto> getReports() {
        return ResponseEntity
                .ok(service.findReports());
    }

}