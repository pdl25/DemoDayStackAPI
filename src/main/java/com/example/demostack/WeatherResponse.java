package com.example.demostack;

public class WeatherResponse {

    public Coord coord;
    public Weather[] weather;
    public Main main;
    public String name;
    public Sys sys;
}

class Coord {
    public Double lon;
    public Double lat;
}

class Wind {
    public Double speed;
    public Integer deg;
    public Double gust;
}
class Clouds {
    public Integer all;
}
