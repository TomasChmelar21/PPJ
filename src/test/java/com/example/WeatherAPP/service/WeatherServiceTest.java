package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import com.example.WeatherAPP.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Ensure mocks are initialized here
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSaveWeatherData() throws Exception {
        // Mock the response for the weather API call
        String jsonResponse = "{\n" +
                "  \"sys\": {\"country\": \"CZ\", \"sunrise\": 1624454400, \"sunset\": 1624508400},\n" +
                "  \"name\": \"Prague\",\n" +
                "  \"coord\": {\"lon\": 14.4208, \"lat\": 50.0880},\n" +
                "  \"main\": {\"temp\": 20.5, \"feels_like\": 18.0, \"temp_min\": 18.0, \"temp_max\": 22.0, \"pressure\": 1015, \"humidity\": 70},\n" +
                "  \"wind\": {\"speed\": 5.0, \"deg\": 270},\n" +
                "  \"visibility\": 10000,\n" +
                "  \"clouds\": {\"all\": 40},\n" +
                "  \"weather\": [{\"main\": \"Clear\", \"description\": \"clear sky\", \"icon\": \"01d\"}]\n" +
                "}";
            String countryJsonResponse = "[{\"name\": {\"common\": \"Czech Republic\"}}]";

            when(restTemplate.getForObject(contains("openweathermap.org"), eq(String.class)))
                    .thenReturn(jsonResponse);
            when(restTemplate.getForObject(contains("restcountries.com"), eq(String.class)))
                    .thenReturn(countryJsonResponse);

            when(countryRepository.findByCode("CZ")).thenReturn(null);

            Country mockCountry = new Country();
            mockCountry.setCode("CZ");
            mockCountry.setName("Czech Republic");
            when(countryRepository.save(any(Country.class))).thenReturn(mockCountry);

            weatherService.saveWeatherData("50.0880", "14.4208");

        }


        @Test
    public void testSaveWeatherDataNoCityFound() throws Exception {
        String jsonResponse = "{\n" +
                "  \"sys\": {\"country\": \"CZ\", \"sunrise\": 1624454400, \"sunset\": 1624508400},\n" +
                "  \"name\": \"Prague\",\n" +
                "  \"coord\": {\"lon\": 14.4208, \"lat\": 50.0880},\n" +
                "  \"main\": {\"temp\": 20.5, \"feels_like\": 18.0, \"temp_min\": 18.0, \"temp_max\": 22.0, \"pressure\": 1015, \"humidity\": 70},\n" +
                "  \"wind\": {\"speed\": 5.0, \"deg\": 270},\n" +
                "  \"visibility\": 10000,\n" +
                "  \"clouds\": {\"all\": 40},\n" +
                "  \"weather\": [{\"main\": \"Clear\", \"description\": \"clear sky\", \"icon\": \"01d\"}]\n" +
                "}";
        String countryJsonResponse = "[{\"name\": {\"common\": \"Czech Republic\"}}]";

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse)
                .thenReturn(countryJsonResponse);

        Country mockCountry = new Country();
        mockCountry.setCode("CZ");
        mockCountry.setName("Czech Republic");
        when(countryRepository.findByCode("CZ")).thenReturn(mockCountry);
        when(cityRepository.findByNameAndCountryCode("Prague", "CZ")).thenReturn(null);

        City mockCity = new City();
        mockCity.setName("Prague");
        mockCity.setLat(50.0880);
        mockCity.setLon(14.4208);
        mockCity.setCountry(mockCountry);
        when(cityRepository.save(any(City.class))).thenReturn(mockCity);

        weatherService.saveWeatherData("50.0880", "14.4208");

    }
}
