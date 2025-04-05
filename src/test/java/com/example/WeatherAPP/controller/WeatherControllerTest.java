package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.WeatherRecord;
import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private WeatherController weatherController;


    @Test
    void testDeleteWeather() throws Exception {
        Long weatherRecordId = 1L;
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();

        mockMvc.perform(post("/weatherData/weather/delete/{id}", weatherRecordId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(weatherMeasurementRepository, times(1)).deleteById(weatherRecordId);
    }

    @Test
    void testDeleteCountry() throws Exception {
        Long countryId = 1L;
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();

        // Act & Assert
        mockMvc.perform(post("/weatherData/country/delete/{id}", countryId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(countryRepository, times(1)).deleteById(countryId);
    }

    @Test
    void testDeleteCity() throws Exception {
        Long cityId = 1L;
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
        mockMvc.perform(post("/weatherData/city/delete/{id}", cityId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(cityRepository, times(1)).deleteById(cityId);
    }
}
