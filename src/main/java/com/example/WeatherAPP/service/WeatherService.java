package com.example.WeatherAPP.service;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.repository.CityRepository;
import com.example.WeatherAPP.repository.CountryRepository;
import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class WeatherService {

    private final String API_KEY = "*";
    private final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";
    private final String COUNTRY_URL = "https://restcountries.com/v3.1/alpha/%s";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void saveWeatherData(String lat, String lon) {
        String requestUrl = String.format(WEATHER_URL, lat, lon, API_KEY);
        logger.info("Sending request to URL: " + requestUrl);
        String jsonResponse = restTemplate.getForObject(requestUrl, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            String countryCode = rootNode.path("sys").path("country").asText();
            Country country = countryRepository.findByCode(countryCode);

            if (country == null) {
                country = new Country();
                country.setCode(countryCode);

                String countryInfoUrl = String.format(COUNTRY_URL, countryCode);
                String countryJsonResponse = restTemplate.getForObject(countryInfoUrl, String.class);
                JsonNode countryInfoNode = objectMapper.readTree(countryJsonResponse);

                String countryName = countryInfoNode.get(0).path("name").path("common").asText();
                country.setName(countryName);

                countryRepository.save(country);
                logger.info("Country data saved: " + countryName);
            }

            String cityName = rootNode.path("name").asText();
            City city = cityRepository.findByNameAndCountryCode(cityName, countryCode);
            if (city == null) {
                city = new City();
                city.setName(cityName);
                city.setLon(rootNode.path("coord").path("lon").asDouble());
                city.setLat(rootNode.path("coord").path("lat").asDouble());
                city.setCountry(country);
                cityRepository.save(city);
            }

            WeatherMeasurement weatherMeasurement = new WeatherMeasurement();
            weatherMeasurement.setCity(city);
            weatherMeasurement.setTimestamp(new Date(rootNode.path("dt").asLong() * 1000));
            weatherMeasurement.setTemp(rootNode.path("main").path("temp").asDouble());
            weatherMeasurement.setFeelsLike(rootNode.path("main").path("feels_like").asDouble());
            weatherMeasurement.setTempMin(rootNode.path("main").path("temp_min").asDouble());
            weatherMeasurement.setTempMax(rootNode.path("main").path("temp_max").asDouble());
            weatherMeasurement.setPressure(rootNode.path("main").path("pressure").asInt());
            weatherMeasurement.setHumidity(rootNode.path("main").path("humidity").asInt());
            weatherMeasurement.setVisibility(rootNode.path("visibility").asInt());
            weatherMeasurement.setWindSpeed(rootNode.path("wind").path("speed").asDouble());
            weatherMeasurement.setWindDeg(rootNode.path("wind").path("deg").asInt());
            weatherMeasurement.setWindGust(rootNode.path("wind").path("gust").asDouble());
            weatherMeasurement.setClouds(rootNode.path("clouds").path("all").asInt());
            weatherMeasurement.setSunrise(new Date(rootNode.path("sys").path("sunrise").asLong() * 1000));
            weatherMeasurement.setSunset(new Date(rootNode.path("sys").path("sunset").asLong() * 1000));
            weatherMeasurement.setWeatherMain(rootNode.path("weather").get(0).path("main").asText());
            weatherMeasurement.setWeatherDescription(rootNode.path("weather").get(0).path("description").asText());
            weatherMeasurement.setWeatherIcon(rootNode.path("weather").get(0).path("icon").asText());

            weatherMeasurementRepository.save(weatherMeasurement);

            logger.info("Weather data saved to MySQL: " + weatherMeasurement);
        } catch (Exception e) {
            logger.error("Error processing weather data", e);
        }
    }

}
