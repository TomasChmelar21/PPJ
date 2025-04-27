package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CountryService;
import com.example.WeatherAPP.service.WeatherMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CountryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CountryService countryService;

    @Mock
    private WeatherMeasurementService weatherMeasurementService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    void testShowEditForm() throws Exception {
        Country country = new Country();
        country.setId(4L);
        country.setName("Yemen");
        country.setCode("YE");

        when(countryService.getCountryById(4L)).thenReturn(country);

        mockMvc.perform(get("/country/edit/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_country"))
                .andExpect(model().attributeExists("country"))
                .andExpect(model().attribute("country", country));
    }

    @Test
    void testUpdateCountry() throws Exception {
        Country country = new Country();
        country.setId(4L);
        country.setName("Yemen");
        country.setCode("YE");

        when(countryService.getCountryById(4L)).thenReturn(country);

        mockMvc.perform(post("/country/edit/4")
                        .param("name", "Yemen Updated")
                        .param("code", "YE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(countryService, times(1)).updateCountry(eq(4L), any(Country.class));
    }

    @Test
    void testQuickUpdateCountryName() throws Exception {
        Country country = new Country();
        country.setId(4L);
        country.setName("Yemen");
        country.setCode("YE");

        when(countryService.getCountryById(4L)).thenReturn(country);

        mockMvc.perform(get("/country/edit/4/name=Yemennn"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(countryService, times(1)).updateCountry(eq(4L), any(Country.class));
        assert country.getName().equals("Yemennn");
    }

    @Test
    void testQuickUpdateCountryCode() throws Exception {
        Country country = new Country();
        country.setId(4L);
        country.setName("Yemen");
        country.setCode("YE");

        when(countryService.getCountryById(4L)).thenReturn(country);

        mockMvc.perform(get("/country/edit/4/code=YEM"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(countryService, times(1)).updateCountry(eq(4L), any(Country.class));
        assert country.getCode().equals("YEM");
    }

    @Test
    void testQuickUpdateCountryInvalidField() throws Exception {
        Country country = new Country();
        country.setId(4L);
        country.setName("Yemen");
        country.setCode("YE");

        when(countryService.getCountryById(4L)).thenReturn(country);

        mockMvc.perform(get("/country/edit/4/invalidField=SomeValue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(countryService, times(0)).updateCountry(eq(4L), any(Country.class));
    }

    @Test
    void testCountryNotFound() throws Exception {
        when(countryService.getCountryById(4L)).thenReturn(null);

        mockMvc.perform(get("/country/edit/4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/country/edit"));
    }
}
