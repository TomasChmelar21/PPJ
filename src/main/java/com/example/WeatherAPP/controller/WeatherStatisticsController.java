package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/prumer")
public class WeatherStatisticsController {

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
        Date today = new Date();
        Date oneDayAgo = calculateDate(1);
        Double avgTempDay = weatherMeasurementRepository.getAverageTemperatureForLastDay(oneDayAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTempDay != null ? avgTempDay : 0);
        return response;
    }

    @GetMapping("/tyden")
    @ResponseBody
    public Map<String, Double> getAverageTemperatureLastWeek() {
        Date today = new Date();
        Date oneWeekAgo = calculateDate(7);
        Double avgTempWeek = weatherMeasurementRepository.getAverageTemperatureForLastWeek(oneWeekAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTempWeek != null ? avgTempWeek : 0);
        return response;
    }

    @GetMapping("/14dni")
    @ResponseBody
    public Map<String, Double> getAverageTemperatureLast14Days() {
        Date today = new Date();
        Date fourteenDaysAgo = calculateDate(14);
        Double avgTemp14Days = weatherMeasurementRepository.getAverageTemperatureForLast14Days(fourteenDaysAgo, today);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", avgTemp14Days != null ? avgTemp14Days : 0);
        return response;
    }

    @GetMapping("")
    public String getAverageTemperaturesPage(Model model) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date oneDayAgo = calendar.getTime();

        calendar.setTime(today);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeekAgo = calendar.getTime();

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, -14);
        Date fourteenDaysAgo = calendar.getTime();


        Double avgTempDay = weatherMeasurementRepository.getAverageTemperatureForLastDay(oneDayAgo, today);
        Double avgTempWeek = weatherMeasurementRepository.getAverageTemperatureForLastWeek(oneWeekAgo, today);
        Double avgTemp14Days = weatherMeasurementRepository.getAverageTemperatureForLast14Days(fourteenDaysAgo, today);


        model.addAttribute("avgTempDay", avgTempDay != null ? avgTempDay : 0);
        model.addAttribute("avgTempWeek", avgTempWeek != null ? avgTempWeek : 0);
        model.addAttribute("avgTemp14Days", avgTemp14Days != null ? avgTemp14Days : 0);

        return "prumer";
    }
}