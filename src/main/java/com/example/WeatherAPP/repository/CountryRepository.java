package com.example.WeatherAPP.repository;

import com.example.WeatherAPP.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCode(String code);
}
