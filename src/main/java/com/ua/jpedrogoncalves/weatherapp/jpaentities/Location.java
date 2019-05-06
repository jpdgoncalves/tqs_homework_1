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
public class Location {
    
    //globalIdLocal
    @Id
    private long id;
    
    //local
    private String location;

    public Location() {
    }

    public Location(long id, String location) {
        this.id = id;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    
}
