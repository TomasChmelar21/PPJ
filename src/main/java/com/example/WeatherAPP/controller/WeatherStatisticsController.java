package com.example.WeatherAPP.controller;

import com.example.WeatherAPP.repository.WeatherMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
public class WeatherStatisticsController {

    @Autowired
    private WeatherMeasurementRepository weatherMeasurementRepository;


    @GetMapping("/prumer")
    public String getAverageTemperatures(Model model) {
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

        Double avgTempDay = weatherMeasurementRepository.getAverageTemperatureForLastDay(oneDayAgo);
        Double avgTempWeek = weatherMeasurementRepository.getAverageTemperatureForLastWeek(oneWeekAgo);
        Double avgTemp14Days = weatherMeasurementRepository.getAverageTemperatureForLast14Days(fourteenDaysAgo);

        model.addAttribute("avgTempDay", avgTempDay != null ? avgTempDay : 0);
        model.addAttribute("avgTempWeek", avgTempWeek != null ? avgTempWeek : 0);
        model.addAttribute("avgTemp14Days", avgTemp14Days != null ? avgTemp14Days : 0);

        return "prumer";
    }
}
