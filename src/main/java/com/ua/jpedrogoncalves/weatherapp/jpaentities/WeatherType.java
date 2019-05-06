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
public class WeatherType {
    
    //idWeatherType
    @Id
    private int id;
    
    //descIdWeatherTypePT
    private String desc;

    public WeatherType() {
    }

    public WeatherType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
}
