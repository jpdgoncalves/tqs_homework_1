/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.jpedrogoncalves.weatherapp.restentities;

/**
 *
 * @author Jose
 */
public class WeatherResponse {
    
    private String location;
    private String weather;
    private int min_temperature;
    private int max_temperature;

    public WeatherResponse() {
    }

    public WeatherResponse(String location, String weather, int min_temperature, int max_temperature) {
        this.location = location;
        this.weather = weather;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getMin_temperature() {
        return min_temperature;
    }

    public void setMin_temperature(int min_temperature) {
        this.min_temperature = min_temperature;
    }

    public int getMax_temperature() {
        return max_temperature;
    }

    public void setMax_temperature(int max_temperature) {
        this.max_temperature = max_temperature;
    }
    
    
}
