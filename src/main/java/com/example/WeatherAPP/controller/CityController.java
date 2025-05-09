package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.model.City;
import com.example.WeatherAPP.model.Country;
import com.example.WeatherAPP.service.CityService;
import com.example.WeatherAPP.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/city")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private CountryService countryService;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.debug("Zahájeno načítání formuláře pro úpravu města s ID {}", id);

        City city = cityService.getCityById(id);
        if (city == null) {
            logger.warn("Město s ID {} nebylo nalezeno", id);
            return "redirect:/edit";
        }

        model.addAttribute("city", city);
        model.addAttribute("countries", countryService.getAllCountries());

        logger.info("Načten formulář pro město: {}", city.getName());
        return "edit_city";
    }

    @PostMapping("/edit/{id}")
    public String updateCity(@PathVariable Long id, @ModelAttribute City city) {
        logger.debug("Pokus o aktualizaci města s ID {}", id);

        Long countryId = city.getCountry().getId();
        Country country = countryService.getCountryById(countryId);
        city.setCountry(country);

        cityService.updateCity(id, city);

        logger.info("Město s ID {} bylo úspěšně aktualizováno", id);
        return "redirect:/edit";
    }

    @GetMapping("/edit/{id}/{fieldValue}")
    public String quickUpdateCity(@PathVariable Long id, @PathVariable String fieldValue) {
        logger.debug("Rychlá aktualizace města ID {} s hodnotou {}", id, fieldValue);

        City city = cityService.getCityById(id);
        if (city == null) {
            logger.warn("Město s ID {} nebylo nalezeno při rychlé aktualizaci", id);
            return "redirect:/edit";
        }

        boolean validUpdate = false;

        if (fieldValue.contains("=")) {
            String[] parts = fieldValue.split("=", 2);
            String field = parts[0];
            String value = parts[1];

            switch (field) {
                case "name":
                    city.setName(value);
                    validUpdate = true;
                    logger.info("Městu ID {} změněno jméno na '{}'", id, value);
                    break;
                case "countryId":
                    try {
                        Long countryId = Long.parseLong(value);
                        Country country = countryService.getCountryById(countryId);
                        if (country != null) {
                            city.setCountry(country);
                            validUpdate = true;
                            logger.info("Městu ID {} změněna země na '{}'", id, country.getName());
                        } else {
                            logger.warn("Země s ID {} nebyla nalezena", countryId);
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Neplatné ID země: '{}'", value, e);
                    }
                    break;
                default:
                    logger.warn("Neznámé pole '{}' pro rychlou aktualizaci", field);
            }

            if (validUpdate) {
                cityService.updateCity(id, city);
                logger.debug("Město ID {} aktualizováno přes rychlou změnu", id);
            }
        } else {
            logger.warn("Neplatný formát fieldValue: '{}'", fieldValue);
        }

        return "redirect:/edit";
    }
}
