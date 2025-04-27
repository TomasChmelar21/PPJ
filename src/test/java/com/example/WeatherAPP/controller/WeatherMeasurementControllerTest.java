package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.WeatherMeasurement;
import com.example.WeatherAPP.service.CityService;
import com.example.WeatherAPP.service.CountryService;
import com.example.WeatherAPP.service.WeatherMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WeatherMeasurementControllerTest {

    @Mock
    private WeatherMeasurementService weatherMeasurementService;

    @Mock
    private CountryService countryService;

    @Mock
    private CityService cityService;

    @InjectMocks
    private WeatherMeasurementController weatherMeasurementController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherMeasurementController).build();
    }

    @Test
    void testShowEditForm_ValidId() throws Exception {
        WeatherMeasurement measurement = new WeatherMeasurement();
        measurement.setId(1L);
        when(weatherMeasurementService.getMeasurementById(1L)).thenReturn(measurement);

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_form"))
                .andExpect(model().attributeExists("measurement"));
    }

    @Test
    void testShowEditForm_InvalidId() throws Exception {
        when(weatherMeasurementService.getMeasurementById(1L)).thenReturn(null);

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/edit"));
    }

    @Test
    void testUpdateMeasurement() throws Exception {
        WeatherMeasurement existingMeasurement = new WeatherMeasurement();
        existingMeasurement.setId(1L);
        when(weatherMeasurementService.getMeasurementById(1L)).thenReturn(existingMeasurement);
        when(weatherMeasurementService.saveMeasurement(any(WeatherMeasurement.class))).thenReturn(existingMeasurement);

        mockMvc.perform(post("/edit/1")
                        .param("id", "1")
                        .param("temp", "25.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/edit"));

        verify(weatherMeasurementService, times(1)).saveMeasurement(any(WeatherMeasurement.class));
    }

    @Test
    void testUpdateMeasurement_InvalidId() throws Exception {
        when(weatherMeasurementService.getMeasurementById(1L)).thenReturn(null);

        mockMvc.perform(post("/edit/1")
                        .param("id", "1")
                        .param("temp", "25.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/edit"));
        verify(weatherMeasurementService, times(0)).saveMeasurement(any(WeatherMeasurement.class));
    }

    // --------- Přidané testy pro rychlou editaci field=value ------------

    @Test
    void testQuickUpdateMeasurement_Humidity() throws Exception {
        WeatherMeasurement measurement = new WeatherMeasurement();
        measurement.setId(5L);
        measurement.setHumidity(50); // původní hodnota

        when(weatherMeasurementService.getMeasurementById(5L)).thenReturn(measurement);

        mockMvc.perform(get("/edit/5/humidity=91"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(weatherMeasurementService).saveMeasurement(argThat(m -> m.getHumidity() == 91));
    }

    @Test
    void testQuickUpdateMeasurement_InvalidField() throws Exception {
        WeatherMeasurement measurement = new WeatherMeasurement();
        measurement.setId(5L);

        when(weatherMeasurementService.getMeasurementById(5L)).thenReturn(measurement);

        mockMvc.perform(get("/edit/5/invalidField=123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(weatherMeasurementService, never()).saveMeasurement(any());
    }

    @Test
    void testQuickUpdateMeasurement_MeasurementNotFound() throws Exception {
        when(weatherMeasurementService.getMeasurementById(5L)).thenReturn(null);

        mockMvc.perform(get("/edit/5/humidity=91"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(weatherMeasurementService, never()).saveMeasurement(any());
    }
}
