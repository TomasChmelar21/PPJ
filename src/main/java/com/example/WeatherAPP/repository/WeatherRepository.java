package com.example.WeatherAPP.repository;

import com.example.WeatherAPP.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherRepository extends MongoRepository<WeatherData, String> {

}
