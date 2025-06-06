package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/weatherData")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    // Smazání měření počasí
    @PostMapping("/weather/delete/{id}")
    public String deleteWeather(@PathVariable Long id) {
        logger.info("Mazání záznamu o počasí s ID {}", id);
        weatherMeasurementRepository.deleteById(id);
        logger.debug("Záznam o počasí s ID {} byl smazán", id);
        return "redirect:/edit";
    }

    // Smazání státu
    @PostMapping("/country/delete/{id}")
    public String deleteCountry(@PathVariable Long id) {
        logger.info("Mazání země s ID {}", id);
        countryRepository.deleteById(id);
        logger.debug("Země s ID {} byla smazána", id);
        return "redirect:/edit";
    }

    // Smazání města
    @PostMapping("/city/delete/{id}")
    public String deleteCity(@PathVariable Long id) {
        logger.info("Mazání města s ID {}", id);
        cityRepository.deleteById(id);
        logger.debug("Město s ID {} bylo smazáno", id);
        return "redirect:/edit";
    }
}
