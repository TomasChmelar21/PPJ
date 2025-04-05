package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.WeatherRecord;
import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/weatherData")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<WeatherRecord> getAllWeatherData() {
        List<WeatherRecord> weatherRecords = mongoTemplate.findAll(WeatherRecord.class, "weather");

        if (weatherRecords.isEmpty()) {
            logger.info("No weather data found in MongoDB.");
        } else {
            logger.info("Fetched " + weatherRecords.size() + " weather records from MongoDB.");
            for (WeatherRecord record : weatherRecords) {
                logger.info("Weather Record: " + record);
                logger.info("Base: " + record.getBase());
            }
        }

        return weatherRecords;
    }

    // Smazání měření počasí
    @PostMapping("/weather/delete/{id}")
    public String deleteWeather(@PathVariable Long id) {
        weatherMeasurementRepository.deleteById(id);
        return "redirect:/edit";
    }

    // Smazání státu
    @PostMapping("/country/delete/{id}")
    public String deleteCountry(@PathVariable Long id) {
        countryRepository.deleteById(id);
        return "redirect:/edit";
    }

    // Smazání města
    @PostMapping("/city/delete/{id}")
    public String deleteCity(@PathVariable Long id) {
        // Předpokládáme, že máte repository pro města
        cityRepository.deleteById(id);
        return "redirect:/edit";
    }


}
