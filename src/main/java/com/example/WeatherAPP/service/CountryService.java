package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    public void updateCountry(Long id, Country newCountryData) {
        Country existingCountry = getCountryById(id);
        if (existingCountry != null) {
            existingCountry.setName(newCountryData.getName());
            existingCountry.setCode(newCountryData.getCode());
            countryRepository.save(existingCountry);
        }
    }
}
