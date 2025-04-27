package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.service.CityService;
import com.example.WeatherAPP.service.CountryService;
import com.example.WeatherAPP.service.WeatherMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherMeasurementController {

    @Autowired
    private WeatherMeasurementService service;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @GetMapping("/edit")
    public String editMeasurements(Model model) {
        model.addAttribute("measurements", service.getAllMeasurements());
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("cities", cityService.getAllCities());
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        WeatherMeasurement measurement = service.getMeasurementById(id);
        if (measurement == null) {
            return "redirect:/edit";
        }
        model.addAttribute("measurement", measurement);
        return "edit_form";
    }

    @PostMapping("/edit/{id}")
    public String updateMeasurement(@PathVariable("id") Long id, @ModelAttribute WeatherMeasurement measurement) {
        WeatherMeasurement existingMeasurement = service.getMeasurementById(id);

        if (existingMeasurement != null) {
            measurement.setCity(existingMeasurement.getCity());

            if (measurement.getTimestamp() == null) measurement.setTimestamp(existingMeasurement.getTimestamp());
            if (measurement.getTemp() == null) measurement.setTemp(existingMeasurement.getTemp());
            if (measurement.getFeelsLike() == null) measurement.setFeelsLike(existingMeasurement.getFeelsLike());
            if (measurement.getTempMin() == null) measurement.setTempMin(existingMeasurement.getTempMin());
            if (measurement.getTempMax() == null) measurement.setTempMax(existingMeasurement.getTempMax());
            if (measurement.getPressure() == null) measurement.setPressure(existingMeasurement.getPressure());
            if (measurement.getHumidity() == null) measurement.setHumidity(existingMeasurement.getHumidity());
            if (measurement.getVisibility() == null) measurement.setVisibility(existingMeasurement.getVisibility());
            if (measurement.getWindSpeed() == null) measurement.setWindSpeed(existingMeasurement.getWindSpeed());
            if (measurement.getWindDeg() == null) measurement.setWindDeg(existingMeasurement.getWindDeg());
            if (measurement.getWindGust() == null) measurement.setWindGust(existingMeasurement.getWindGust());
            if (measurement.getClouds() == null) measurement.setClouds(existingMeasurement.getClouds());
            if (measurement.getSunrise() == null) measurement.setSunrise(existingMeasurement.getSunrise());
            if (measurement.getSunset() == null) measurement.setSunset(existingMeasurement.getSunset());
            if (measurement.getWeatherMain() == null) measurement.setWeatherMain(existingMeasurement.getWeatherMain());
            if (measurement.getWeatherDescription() == null) measurement.setWeatherDescription(existingMeasurement.getWeatherDescription());
            if (measurement.getWeatherIcon() == null) measurement.setWeatherIcon(existingMeasurement.getWeatherIcon());
        } else {
            return "redirect:/edit";
        }

        service.saveMeasurement(measurement);
        return "redirect:/edit";
    }

    @GetMapping("/edit/{id}/{field}={value}")
    public String quickUpdateMeasurement(
            @PathVariable("id") Long id,
            @PathVariable("field") String field,
            @PathVariable("value") String value
    ) {
        WeatherMeasurement measurement = service.getMeasurementById(id);

        if (measurement == null) {
            return "redirect:/edit";
        }

        switch (field) {
            case "temp":
                measurement.setTemp(Double.valueOf(value));
                break;
            case "humidity":
                measurement.setHumidity(Integer.valueOf(value));
                break;
            case "pressure":
                measurement.setPressure(Integer.valueOf(value));
                break;
            case "windSpeed":
                measurement.setWindSpeed(Double.valueOf(value));
                break;
            case "windDeg":
                measurement.setWindDeg(Integer.valueOf(value));
                break;
            case "clouds":
                measurement.setClouds(Integer.valueOf(value));
                break;
            case "visibility":
                measurement.setVisibility(Integer.valueOf(value));
                break;
            case "feelsLike":
                measurement.setFeelsLike(Double.valueOf(value));
                break;
            case "tempMin":
                measurement.setTempMin(Double.valueOf(value));
                break;
            case "tempMax":
                measurement.setTempMax(Double.valueOf(value));
                break;
            default:
                return "redirect:/edit";
        }

        service.saveMeasurement(measurement);

        return "redirect:/edit";
    }

}
