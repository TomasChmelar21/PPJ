package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherMeasurementService {

    @Autowired
    private WeatherMeasurementRepository repository;

    public List<WeatherMeasurement> getAllMeasurements() {
        return repository.findAll();
    }

    public WeatherMeasurement getMeasurementById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Modify this method to return the saved WeatherMeasurement
    public WeatherMeasurement saveMeasurement(WeatherMeasurement measurement) {
        return repository.save(measurement);  // Save and return the saved entity
    }
}
