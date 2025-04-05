package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.startsWith;

public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @Mock
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Model model;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testHome() {
        String viewName = homeController.home();

        assert viewName.equals("index");
    }

    @Test
    public void testGetWeather() throws Exception {
        String mockResponse = "{\"coord\":{\"lon\":14.4208,\"lat\":50.088},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}]}";

        when(restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?lat=50.088&lon=14.4208&appid=09bb23d79b6a513a2251168a3a6c933d&units=metric",
                String.class))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/weather")
                        .param("lat", "50.088")
                        .param("lon", "14.4208"))
                .andExpect(status().isOk())  // Check if the status is OK
                .andExpect(model().attribute("response", startsWith("{\"coord\":{\"lon\"")));
    }
}
