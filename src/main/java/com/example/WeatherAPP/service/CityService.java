package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public void updateCity(Long id, City newCityData) {
        City existingCity = getCityById(id);
        if (existingCity != null) {
            existingCity.setName(newCityData.getName());
            existingCity.setCountry(newCityData.getCountry());
            cityRepository.save(existingCity);
        }
    }
}
