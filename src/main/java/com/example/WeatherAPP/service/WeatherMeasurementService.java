package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class WeatherMeasurementService {

    @Autowired
    private WeatherMeasurementRepository repository;

    public List<WeatherMeasurement> getAllMeasurements() {
        Iterable<WeatherMeasurement> weatherMeasurementsIterable = repository.findAll();
        List<WeatherMeasurement> weatherMeasurements = new ArrayList<>();
        weatherMeasurementsIterable.forEach(weatherMeasurements::add);

        return weatherMeasurements;
    }

    public WeatherMeasurement getMeasurementById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public WeatherMeasurement saveMeasurement(WeatherMeasurement measurement) {
        return repository.save(measurement);
    }
}

