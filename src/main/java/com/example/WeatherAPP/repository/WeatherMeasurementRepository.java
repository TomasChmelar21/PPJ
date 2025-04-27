package com.example.WeatherAPP.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.WeatherAPP.model.WeatherMeasurement;
import org.springframework.data.repository.query.Param;
import java.util.Date;

public interface WeatherMeasurementRepository extends CrudRepository<WeatherMeasurement, Long> {

    @Query("SELECT AVG(w.tempMax) FROM WeatherMeasurement w WHERE DATE(w.timestamp) BETWEEN DATE(:oneDayAgo) AND DATE(:today)")
    Double getAverageTemperatureForLastDay(@Param("oneDayAgo") Date oneDayAgo, @Param("today") Date today);

    @Query("SELECT AVG(w.tempMax) FROM WeatherMeasurement w WHERE DATE(w.timestamp) BETWEEN DATE(:oneWeekAgo) AND DATE(:today)")
    Double getAverageTemperatureForLastWeek(@Param("oneWeekAgo") Date oneWeekAgo, @Param("today") Date today);

    @Query("SELECT AVG(w.tempMax) FROM WeatherMeasurement w WHERE DATE(w.timestamp) BETWEEN DATE(:fourteenDaysAgo) AND DATE(:today)")
    Double getAverageTemperatureForLast14Days(@Param("fourteenDaysAgo") Date fourteenDaysAgo, @Param("today") Date today);
}