package com.example.demo.services

import com.example.demo.entities.Client
import com.example.demo.entities.dto.ClientInfoDto
import com.example.demo.entities.dto.ReportBuilder
import com.example.demo.repositories.ClientRepository
import com.example.demo.repositories.ReportRepository
import org.modelmapper.ModelMapper
import spock.lang.Specification
import spock.lang.Subject

import java.time.DateTimeException

class AppServiceImplSpec extends Specification {

    def clientRepository = Mock(ClientRepository)
    def reportRepository = Mock(ReportRepository)
    def mapper = Mock(ModelMapper)

    @Subject
    def service = Spy(AppServiceImpl, constructorArgs: [clientRepository, reportRepository, mapper])


    def "get clients"() {
        given:
        clientRepository.findAll() >> clients

        when:
        def actualClientInfo = service.getClients()

        then:
        actualClientInfo == expectedClientInfo

        where:
        clients                        | expectedClientInfo
        [buildClient("M", "A", "555")] | ClientInfoDto.builder().clients([buildClient("M", "A", "555")]).total(1).build();
        [] | ClientInfoDto.builder().clients([]).total(0).build();
    }


    def "if client full name is empty or null then IllegalArgumentException exception will be throw"() {
        when:
        service.populateFullName(fullName)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "Client full name is required!"

        where:
        fullName << [null, ""]
    }


    def "populate client full name"() {
        when:
        def actualFullName = service.populateFullName(fullName)

        then:
        actualFullName == fullName

        where:
        fullName << ["alex", " a "]
    }


    def "populate price"() {
        when:
        def actualPrice = service.populatePrice(price)

        then:
        actualPrice == expectedPrice

        where:
        price                  | expectedPrice
        new BigDecimal(1)      | "1"
        new BigDecimal(123456) | "123456"
        null                   | null
    }


    def "populate phone number"() {
        when:
        def actualPhone = service.populatePhone(phone)

        then:
        actualPhone == phone

        where:
        phone << ["555-555-555", "", null]
    }


    def "parse month"() {
        when:
        def actualMonthName = service.parseMonth(month)

        then:
        actualMonthName == expectedMonthName

        where:
        month               | expectedMonthName
        1                   | "January"
        12                  | "December"
        new BigInteger("1") | "January"
    }


    def "if month number is incorrect then DateTimeException exception will be throw"() {
        when:
        service.parseMonth(month)

        then:
        def e = thrown(DateTimeException)
        e.getMessage() == "Invalid value for MonthOfYear: " + month

        where:
        month << [0, -1]
    }


    def "convert numbers to month names"() {
        when:
        def actualMonthNames = service.convertNumbersToMonthNames(monthNumbers as List)

        then:
        actualMonthNames == expectedMonthNames

        where:
        monthNumbers | expectedMonthNames
        [1]          | ["January"]
        [12]         | ["December"]
        [1, 12]      | ["January", "December"]
    }


    def "populate report builder"() {
        when:
        def actualReport = service.populateReportBuilder(input as Object[])

        then:
        actualReport == expectedReport

        where:
        input                                              | expectedReport
        buildTestInput("Alex", "5.00", "555-55-5").first() | buildTestInput("Alex", "5.00", "555-55-5").last()
        buildTestInput("Cat", "555.00", "999-342").first() | buildTestInput("Cat", "555.00", "999-342").last()
        buildTestInput("Dog", "0.00", null).first()        | buildTestInput("Dog", "0.00", null).last()
    }


    def buildTestInput(String fullName, String price, String phone) {
        def arr = [fullName,
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   new BigDecimal(price),
                   phone]
        def report = ReportBuilder.builder()
                .fullName(fullName)
                .january(price)
                .february(price)
                .march(price)
                .april(price)
                .may(price)
                .june(price)
                .july(price)
                .august(price)
                .september(price)
                .october(price)
                .november(price)
                .december(price)
                .phone(phone)
                .build();

        return [arr, report]
    }

    def buildClient(String firstName, String lastName, String phone) {
        def client = new Client()
        client.setFirstName(firstName)
        client.setLastName(lastName)
        client.setPhone(phone)
        return client;
    }

}
