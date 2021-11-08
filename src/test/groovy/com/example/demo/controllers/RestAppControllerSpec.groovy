package com.example.demo.controllers

import com.example.demo.entities.dto.ClientDto
import com.example.demo.entities.dto.ReportDto
import com.example.demo.services.AppService
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class RestAppControllerSpec extends Specification {

    def service = Mock(AppService)

    @Subject
    def controller = Spy(RestAppController, constructorArgs: [service])

    def "calling GET method to get clients"() {
        when:
        controller.getClients(firstName, lastName)

        then:
        1 * service.getClients()
        0 * service.getClientsByLastOrFirstName(firstName, lastName)

        where:
        firstName | lastName
        null      | null
        ""        | ""
    }


    def "calling GET method to get clients with parameters: first and last name"() {
        when:
        controller.getClients(firstName, lastName)

        then:
        0 * service.getClients()
        1 * service.getClientsByLastOrFirstName(firstName, lastName)

        where:
        firstName | lastName
        "xxx"     | "yyy"
        "xxx"     | null
        null      | "yyy"
        ""        | "yyy"
        "xxx"     | ""
    }


    def "calling GET method to get all reports"() {
        when:
        controller.getReports()

        then:
        1 * service.findReports()
    }


    def "calling POST method to create new report"() {
        when:
        controller.createReport(reportDto)

        then:
        1 * service.createReport(reportDto)

        where:
        reportDto << [ReportDto.builder()
                              .clientId(1)
                              .price(1)
                              .date(LocalDate.now()).build(),
                      ReportDto.builder()
                              .clientId(1243)
                              .price(99999)
                              .date(LocalDate.now().minusDays(1))
                              .build()
        ]

    }


    def "calling POST method to create new client"() {
        when:
        controller.createClient(clientDto)

        then:
        1 * service.createClient(clientDto)

        where:
        clientDto << [
                ClientDto.builder()
                        .firstName("Alex")
                        .lastName("Malex")
                        .phone("555-555-55")
                        .date(LocalDate.now())
                        .build(),
                ClientDto.builder()
                        .firstName("Stefan")
                        .lastName("Cat")
                        .phone("99-993-43")
                        .date(LocalDate.now().minusYears(1))
                        .build()
        ]
    }


}
