package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CountryService;
import com.example.WeatherAPP.service.WeatherMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private WeatherMeasurementService weatherMeasurementService;

    @GetMapping("/edit")
    public String editCountries(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("measurements", weatherMeasurementService.getAllMeasurements());
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return "redirect:/country/edit";
        }
        model.addAttribute("country", country);
        return "edit_country";
    }

    @PostMapping("/edit/{id}")
    public String updateCountry(@PathVariable Long id, @ModelAttribute Country country) {
        countryService.updateCountry(id, country);
        return "redirect:/edit";
    }

    @GetMapping("/edit/{id}/{fieldValue}")
    public String quickUpdateCountry(@PathVariable Long id, @PathVariable String fieldValue) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return "redirect:/edit";
        }

        if (fieldValue.contains("=")) {
            String[] parts = fieldValue.split("=", 2);
            String field = parts[0];
            String value = parts[1];

            boolean validUpdate = false;

            switch (field) {
                case "name":
                    country.setName(value);
                    validUpdate = true;
                    break;
                case "code":
                    country.setCode(value);
                    validUpdate = true;
                    break;
                default:
                    break;
            }
            if (validUpdate) {
                countryService.updateCountry(id, country);
            }
        }

        return "redirect:/edit";
    }

}
