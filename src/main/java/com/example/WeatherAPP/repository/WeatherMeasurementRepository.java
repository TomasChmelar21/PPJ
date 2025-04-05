package com.example.WeatherAPP.repository;

import com.example.WeatherAPP.model.WeatherMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface WeatherMeasurementRepository extends JpaRepository<WeatherMeasurement, Long> {

    // Query to get the average temperature for the last 24 hours
    @Query("SELECT AVG(w.temp) FROM WeatherMeasurement w WHERE w.timestamp >= :startDate")
    Double getAverageTemperatureForLastDay(Date startDate);

    // Query to get the average temperature for the last week
    @Query("SELECT AVG(w.temp) FROM WeatherMeasurement w WHERE w.timestamp >= :startDate")
    Double getAverageTemperatureForLastWeek(Date startDate);

    // Query to get the average temperature for the last 14 days
    @Query("SELECT AVG(w.temp) FROM WeatherMeasurement w WHERE w.timestamp >= :startDate")
    Double getAverageTemperatureForLast14Days(Date startDate);
}
