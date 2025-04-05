package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CityService;
import com.example.WeatherAPP.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return "redirect:/edit";
        }
        model.addAttribute("city", city);
        model.addAttribute("countries", countryService.getAllCountries());
        return "edit_city";
    }

    @PostMapping("/edit/{id}")
    public String updateCity(@PathVariable Long id, @ModelAttribute City city) {
        Long countryId = city.getCountry().getId();
        Country country = countryService.getCountryById(countryId);
        city.setCountry(country);

        cityService.updateCity(id, city);
        return "redirect:/edit";
    }
}
