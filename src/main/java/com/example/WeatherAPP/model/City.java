package com.example.WeatherAPP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "name", nullable = false)
    @Getter @Setter
    private String name;

    @Column(name = "lon", nullable = false)
    @Getter @Setter
    private double lon;

    @Column(name = "lat", nullable = false)
    @Getter @Setter
    private double lat;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @Getter @Setter
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<WeatherMeasurement> weatherMeasurements;

    public City() {
    }

    public City(String name, double lon, double lat, Country country) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.country = country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
