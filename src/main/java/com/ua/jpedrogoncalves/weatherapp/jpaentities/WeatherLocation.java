/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.jpedrogoncalves.weatherapp.jpaentities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Jose
 */
@Entity
public class WeatherLocation {
    
    @Id
    private long location;
    private int weather;
    private int min_temperature;
    private int max_temperature;
    
    public WeatherLocation() {
        
    }

    public WeatherLocation(long location, int weather, int min_temperature, int max_temperature) {
        this.location = location;
        this.weather = weather;
        this.min_temperature = min_temperature;
        this.max_temperature = max_temperature;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
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
