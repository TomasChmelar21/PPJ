package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/weatherSQL")
public class SQLController {

    private static final Logger logger = LoggerFactory.getLogger(SQLController.class);

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @GetMapping
    public List<WeatherMeasurement> getAllWeatherData() {
        logger.debug("Zahájeno načítání všech záznamů o počasí z MySQL.");
        Iterable<WeatherMeasurement> weatherMeasurementsIterable = weatherMeasurementRepository.findAll();
        List<WeatherMeasurement> weatherMeasurements = new ArrayList<>();
        weatherMeasurementsIterable.forEach(weatherMeasurements::add);

        if (weatherMeasurements.isEmpty()) {
            logger.info("Nebyly nalezeny žádné záznamy o počasí v MySQL.");
        } else {
            logger.info("Načteno {} záznamů o počasí z MySQL.", weatherMeasurements.size());
            for (WeatherMeasurement record : weatherMeasurements) {
                logger.debug("Záznam: ID={}, město={}, teplota={}, stav={}",
                        record.getId(),
                        record.getCity().getName(),
                        record.getTemp(),
                        record.getWeatherMain());
            }
        }

        return weatherMeasurements;
    }
}
