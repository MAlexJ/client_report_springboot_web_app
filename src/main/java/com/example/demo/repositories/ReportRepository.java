package com.example.demo.repositories;

import com.example.demo.entities.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Integer> {

    @Query(value = "SELECT CONCAT(c.first_name, ' ', c.last_name) as full_name,\n" +
            "       sum(if(month(r.date) = 1, CAST(price AS DECIMAL(10, 2)), NULL))  AS january,\n" +
            "       sum(if(month(r.date) = 2, CAST(price AS DECIMAL(10, 2)), NULL))  AS february,\n" +
            "       sum(if(month(r.date) = 3, CAST(price AS DECIMAL(10, 2)), NULL))  AS march,\n" +
            "       sum(if(month(r.date) = 4, CAST(price AS DECIMAL(10, 2)), NULL))  AS april,\n" +
            "       sum(if(month(r.date) = 5, CAST(price AS DECIMAL(10, 2)), NULL))  AS may,\n" +
            "       sum(if(month(r.date) = 6, CAST(price AS DECIMAL(10, 2)), NULL))  AS june,\n" +
            "       sum(if(month(r.date) = 7, CAST(price AS DECIMAL(10, 2)), NULL))  AS july,\n" +
            "       sum(if(month(r.date) = 8, CAST(price AS DECIMAL(10, 2)), NULL))  AS august,\n" +
            "       sum(if(month(r.date) = 9, CAST(price AS DECIMAL(10, 2)), NULL))  AS september,\n" +
            "       sum(if(month(r.date) = 10, CAST(price AS DECIMAL(10, 2)), NULL)) AS october,\n" +
            "       sum(if(month(r.date) = 11, CAST(price AS DECIMAL(10, 2)), NULL)) AS november,\n" +
            "       sum(if(month(r.date) = 12, CAST(price AS DECIMAL(10, 2)), NULL)) AS december,\n" +
            "       c.phone\n" +
            "FROM report as r\n" +
            "         LEFT JOIN client as c ON r.client_id = c.id\n" +
            "GROUP BY r.client_id", nativeQuery = true)
    List<?> findAllReportsGroupByMonth();


    @Query(value = "SELECT DISTINCT MONTH(date) FROM report ORDER BY MONTH(date)", nativeQuery = true)
    List<Integer> findAllReportMonth();
}
