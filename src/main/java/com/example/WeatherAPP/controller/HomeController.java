package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    private final String API_KEY = "09bb23d79b6a513a2251168a3a6c933d";
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";

    @Autowired
    WeatherService weatherService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("lat") String lat,
                             @RequestParam("lon") String lon,
                             Model model) {
        String requestUrl = String.format(URL, lat, lon, API_KEY);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(requestUrl, String.class);

        System.out.println("API Response: " + response);

        model.addAttribute("response", response);

        weatherService.saveWeatherData(lat, lon);

        return "index";
    }
}
