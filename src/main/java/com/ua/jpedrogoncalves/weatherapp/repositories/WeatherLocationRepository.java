/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.jpedrogoncalves.weatherapp.repositories;

import com.ua.jpedrogoncalves.weatherapp.jpaentities.WeatherLocation;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Jose
 */
public interface WeatherLocationRepository extends CrudRepository<WeatherLocation, Long>{
}
