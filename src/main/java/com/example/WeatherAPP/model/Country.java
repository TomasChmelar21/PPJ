package com.example.WeatherAPP.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    @Getter @Setter
    private String code;

    @Column(name = "name", nullable = false)
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "country")
    private List<City> cities;
}
