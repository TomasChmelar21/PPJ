package com.example.WeatherAPP.repository;

import com.example.WeatherAPP.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    City findByNameAndCountryCode(String cityName, String countryCode);
}
