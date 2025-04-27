package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CityService;
import com.example.WeatherAPP.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CityService cityService;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    void testShowEditForm() throws Exception {
        City city = new City();
        city.setId(4L);
        city.setName("ArRaydah");
        Country country = new Country();
        country.setId(1L);
        country.setName("Yemen");
        city.setCountry(country);

        when(cityService.getCityById(4L)).thenReturn(city);
        when(countryService.getAllCountries()).thenReturn(List.of(country));

        mockMvc.perform(get("/city/edit/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_city"))
                .andExpect(model().attributeExists("city"))
                .andExpect(model().attribute("city", city))
                .andExpect(model().attributeExists("countries"));
    }

    @Test
    void testUpdateCity() throws Exception {
        City city = new City();
        city.setId(4L);
        city.setName("ArRaydah");
        Country country = new Country();
        country.setId(1L);
        country.setName("Yemen");
        city.setCountry(country);

        when(cityService.getCityById(4L)).thenReturn(city);
        when(countryService.getAllCountries()).thenReturn(List.of(country));

        mockMvc.perform(post("/city/edit/4")
                        .param("name", "ArRaydah Updated")
                        .param("country.id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(cityService, times(1)).updateCity(eq(4L), any(City.class));
    }

    @Test
    void testQuickUpdateCityName() throws Exception {
        City city = new City();
        city.setId(4L);
        city.setName("ArRaydah");
        Country country = new Country();
        country.setId(1L);
        country.setName("Yemen");
        city.setCountry(country);

        when(cityService.getCityById(4L)).thenReturn(city);

        mockMvc.perform(get("/city/edit/4/name=ArRaydahUpdated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(cityService, times(1)).updateCity(eq(4L), any(City.class));
        assert city.getName().equals("ArRaydahUpdated");
    }

    @Test
    void testQuickUpdateCityCountry() throws Exception {
        City city = new City();
        city.setId(4L);
        city.setName("ArRaydah");
        Country country = new Country();
        country.setId(1L);
        country.setName("Yemen");
        city.setCountry(country);

        Country newCountry = new Country();
        newCountry.setId(2L);
        newCountry.setName("Saudi Arabia");

        when(cityService.getCityById(4L)).thenReturn(city);
        when(countryService.getCountryById(2L)).thenReturn(newCountry);

        mockMvc.perform(get("/city/edit/4/countryId=2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(cityService, times(1)).updateCity(eq(4L), any(City.class));
        assert city.getCountry().equals(newCountry);
    }

    @Test
    void testQuickUpdateCityInvalidField() throws Exception {
        City city = new City();
        city.setId(4L);
        city.setName("ArRaydah");
        Country country = new Country();
        country.setId(1L);
        country.setName("Yemen");
        city.setCountry(country);

        when(cityService.getCityById(4L)).thenReturn(city);

        mockMvc.perform(get("/city/edit/4/invalidField=SomeValue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));

        verify(cityService, times(0)).updateCity(eq(4L), any(City.class));
    }


    @Test
    void testCityNotFound() throws Exception {
        when(cityService.getCityById(4L)).thenReturn(null);

        mockMvc.perform(get("/city/edit/4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/edit"));
    }
}
