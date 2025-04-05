package com.example.WeatherAPP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "weather_measurement")
public class WeatherMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @Getter @Setter
    private City city;

    @Column(name = "timestamp", nullable = false)
    @Getter @Setter
    private Date timestamp;

    @Column(name = "temp", nullable = false)
    @Getter @Setter
    private Double temp;

    @Column(name = "feels_like", nullable = false)
    @Getter @Setter
    private Double feelsLike;

    @Column(name = "temp_min", nullable = false)
    @Getter @Setter
    private Double tempMin;

    @Column(name = "temp_max", nullable = false)
    @Getter @Setter
    private Double tempMax;

    @Column(name = "pressure", nullable = false)
    @Getter @Setter
    private Integer pressure;

    @Column(name = "humidity", nullable = false)
    @Getter @Setter
    private Integer humidity;

    @Column(name = "visibility", nullable = false)
    @Getter @Setter
    private Integer visibility;

    @Column(name = "wind_speed", nullable = false)
    @Getter @Setter
    private Double windSpeed;

    @Column(name = "wind_deg", nullable = false)
    @Getter @Setter
    private Integer windDeg;

    @Column(name = "wind_gust")
    @Getter @Setter
    private Double windGust;

    @Column(name = "clouds", nullable = false)
    @Getter @Setter
    private Integer clouds;

    @Column(name = "sunrise", nullable = false)
    @Getter @Setter
    private Date sunrise;

    @Column(name = "sunset", nullable = false)
    @Getter @Setter
    private Date sunset;

    @Column(name = "weather_main", nullable = false)
    @Getter @Setter
    private String weatherMain;

    @Column(name = "weather_description", nullable = false)
    @Getter @Setter
    private String weatherDescription;

    @Column(name = "weather_icon", nullable = false)
    @Getter @Setter
    private String weatherIcon;
}
