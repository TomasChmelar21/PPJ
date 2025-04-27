package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherStatisticsController.class)
public class WeatherStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherMeasurementRepository weatherMeasurementRepository;

    @BeforeEach
    void setUp() {
        when(weatherMeasurementRepository.getAverageTemperatureForLastDay(Mockito.any(), Mockito.any()))
                .thenReturn(22.0);
        when(weatherMeasurementRepository.getAverageTemperatureForLastWeek(Mockito.any(), Mockito.any()))
                .thenReturn(21.0);
        when(weatherMeasurementRepository.getAverageTemperatureForLast14Days(Mockito.any(), Mockito.any()))
                .thenReturn(20.5);
    }

    @Test
    void testGetAverageTemperatureLastDay() throws Exception {
        mockMvc.perform(get("/prumer/den"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").isNumber());
    }

    @Test
    void testGetAverageTemperatureLastWeek() throws Exception {
        mockMvc.perform(get("/prumer/tyden"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").isNumber());
    }

    @Test
    void testGetAverageTemperatureLast14Days() throws Exception {
        mockMvc.perform(get("/prumer/14dni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageTemperature").isNumber());
    }
}
