    package com.example.WeatherAPP.model;

    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.mongodb.core.mapping.Field;

    import java.util.List;

    @Document(collection = "weather")
    public class WeatherRecord {

        @Getter
        @Setter
        @Id
        private String id;

        @Getter
        @Setter
        @Field("coord")
        private Coord coord;

        @Getter
        @Setter
        @Field("weather")
        private List<Weather> weather;

        @Getter
        @Setter
        @Field("base")
        private String base;


        @Getter
        @Setter
        @Field("main")
        private Main main;

        @Getter
        @Setter
        @Field("visibility")
        private int visibility;

        @Getter
        @Setter
        @Field("wind")
        private Wind wind;

        @Getter
        @Setter
        @Field("clouds")
        private Clouds clouds;

        @Getter
        @Setter
        @Field("dt")
        private long dt;

        @Getter
        @Setter
        @Field("sys")
        private Sys sys;

        @Getter
        @Setter
        @Field("timezone")
        private int timezone;

        @Getter
        @Setter
        @Field("city_id")
        private int cityId;

        @Getter
        @Setter
        @Field("name")
        private String name;

        @Getter
        @Setter
        @Field("cod")
        private int cod;

        public WeatherRecord() {
            this.coord = coord;
            this.weather = weather;
            this.base = base;
            this.main = main;
            this.visibility = visibility;
            this.wind = wind;
            this.clouds = clouds;
            this.dt = dt;
            this.sys = sys;
            this.timezone = timezone;
            this.cityId = cityId;
            this.name = name;
            this.cod = cod;
        }

        @Getter
        @Setter
        public static class Coord {
            private double lon;
            private double lat;

            public Coord(double lon, double lat) {
                this.lon = lon;
                this.lat = lat;
            }
        }

        @Getter
        @Setter
        public static class Weather {
            private int id;
            private String main;
            private String description;
            private String icon;

            public Weather(int id, String main, String description, String icon) {
                this.id = id;
                this.main = main;
                this.description = description;
                this.icon = icon;
            }
        }

        @Getter
        @Setter
        public static class Main {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private int humidity;
            private int sea_level;
            private int grnd_level;

            public Main(double temp, double feels_like, double temp_min, double temp_max, int pressure, int humidity, int sea_level, int grnd_level) {
                this.temp = temp;
                this.feels_like = feels_like;
                this.temp_min = temp_min;
                this.temp_max = temp_max;
                this.pressure = pressure;
                this.humidity = humidity;
                this.sea_level = sea_level;
                this.grnd_level = grnd_level;
            }
        }

        @Getter
        @Setter
        public static class Wind {
            private double speed;
            private int deg;

            public Wind(double speed, int deg) {
                this.speed = speed;
                this.deg = deg;
            }
        }

        @Getter
        @Setter
        public static class Clouds {
            private int all;

            public Clouds(int all) {
                this.all = all;
            }
        }

        @Getter
        @Setter
        public static class Sys {
            private int type;
            private int id;
            private String country;
            private long sunrise;
            private long sunset;

            public Sys(int type, int id, String country, long sunrise, long sunset) {
                this.type = type;
                this.id = id;
                this.country = country;
                this.sunrise = sunrise;
                this.sunset = sunset;
            }
        }
    }
