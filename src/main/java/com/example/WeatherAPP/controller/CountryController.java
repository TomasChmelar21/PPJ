package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CountryService;
import com.example.WeatherAPP.service.WeatherMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/country")
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private WeatherMeasurementService weatherMeasurementService;

    @GetMapping("/edit")
    public String editCountries(Model model) {
        logger.debug("Načítání seznamu zemí a měření");
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("measurements", weatherMeasurementService.getAllMeasurements());
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.debug("Zobrazování formuláře pro zemi s ID {}", id);
        Country country = countryService.getCountryById(id);
        if (country == null) {
            logger.warn("Země s ID {} nebyla nalezena", id);
            return "redirect:/country/edit";
        }
        model.addAttribute("country", country);
        return "edit_country";
    }

    @PostMapping("/edit/{id}")
    public String updateCountry(@PathVariable Long id, @ModelAttribute Country country) {
        logger.info("Aktualizace země s ID {}", id);
        countryService.updateCountry(id, country);
        return "redirect:/edit";
    }

    @GetMapping("/edit/{id}/{fieldValue}")
    public String quickUpdateCountry(@PathVariable Long id, @PathVariable String fieldValue) {
        logger.debug("Rychlá úprava země ID {} s hodnotou {}", id, fieldValue);
        Country country = countryService.getCountryById(id);
        if (country == null) {
            logger.warn("Země s ID {} nebyla nalezena", id);
            return "redirect:/edit";
        }

        boolean validUpdate = false;

        if (fieldValue.contains("=")) {
            String[] parts = fieldValue.split("=", 2);
            String field = parts[0];
            String value = parts[1];

            switch (field) {
                case "name":
                    country.setName(value);
                    validUpdate = true;
                    logger.info("Zemi ID {} změněno jméno na '{}'", id, value);
                    break;
                case "code":
                    country.setCode(value);
                    validUpdate = true;
                    logger.info("Zemi ID {} změněn kód na '{}'", id, value);
                    break;
                default:
                    logger.warn("Neznámé pole '{}' pro aktualizaci", field);
                    break;
            }

            if (validUpdate) {
                countryService.updateCountry(id, country);
                logger.debug("Země ID {} byla aktualizována", id);
            }
        } else {
            logger.warn("Neplatný formát fieldValue: '{}'", fieldValue);
        }

        return "redirect:/edit";
    }
}
