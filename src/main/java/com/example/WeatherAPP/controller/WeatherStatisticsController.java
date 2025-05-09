package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/prumer")
public class WeatherStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherStatisticsController.class);

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;

    private Date calculateDate(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
        return calendar.getTime();
    }

    @GetMapping("/den")
    @ResponseBody
    public Map<String, Double> getAverageTemperatureLastDay() {
        logger.info("Výpočet průměrné teploty za poslední den.");
        Date today = new Date();
        Date oneDayAgo = calculateDate(1);
        Double avgTempDay = weatherMeasurementRepository.getAverageTemperatureForLastDay(oneDayAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTempDay != null ? avgTempDay : 0);
        logger.info("Průměrná teplota za poslední den: {}", avgTempDay);
        return response;
    }

    @GetMapping("/tyden")
    @ResponseBody
    public Map<String, Double> getAverageTemperatureLastWeek() {
        logger.info("Výpočet průměrné teploty za poslední týden.");
        Date today = new Date();
        Date oneWeekAgo = calculateDate(7);
        Double avgTempWeek = weatherMeasurementRepository.getAverageTemperatureForLastWeek(oneWeekAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTempWeek != null ? avgTempWeek : 0);
        logger.info("Průměrná teplota za poslední týden: {}", avgTempWeek);
        return response;
    }

    @GetMapping("/14dni")
    @ResponseBody
    public Map<String, Double> getAverageTemperatureLast14Days() {
        logger.info("Výpočet průměrné teploty za posledních 14 dní.");
        Date today = new Date();
        Date fourteenDaysAgo = calculateDate(14);
        Double avgTemp14Days = weatherMeasurementRepository.getAverageTemperatureForLast14Days(fourteenDaysAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTemp14Days != null ? avgTemp14Days : 0);
        logger.info("Průměrná teplota za posledních 14 dní: {}", avgTemp14Days);
        return response;
    }

    @GetMapping("")
    public String getAverageTemperaturesPage(Model model) {
        logger.info("Načítání stránky průměrných teplot.");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        Double avgTempDay = weatherMeasurementRepository.getAverageTemperatureForLastDay(calculateDate(1), today);
        Double avgTempWeek = weatherMeasurementRepository.getAverageTemperatureForLastWeek(calculateDate(7), today);
        Double avgTemp14Days = weatherMeasurementRepository.getAverageTemperatureForLast14Days(calculateDate(14), today);

        model.addAttribute("avgTempDay", avgTempDay != null ? avgTempDay : 0);
        model.addAttribute("avgTempWeek", avgTempWeek != null ? avgTempWeek : 0);
        model.addAttribute("avgTemp14Days", avgTemp14Days != null ? avgTemp14Days : 0);

        logger.info("Průměrné teploty za dnešní den: {}, týden: {}, 14 dní: {}", avgTempDay, avgTempWeek, avgTemp14Days);
        return "prumer";
    }
}
