/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.jpedrogoncalves.weatherapp.restcontrolers;

import com.ua.jpedrogoncalves.weatherapp.RestDataHolder;
import com.ua.jpedrogoncalves.weatherapp.jpaentities.Location;
import com.ua.jpedrogoncalves.weatherapp.jpaentities.WeatherLocation;
import com.ua.jpedrogoncalves.weatherapp.jpaentities.WeatherType;
import com.ua.jpedrogoncalves.weatherapp.repositories.LocationRepository;
import com.ua.jpedrogoncalves.weatherapp.repositories.WeatherLocationRepository;
import com.ua.jpedrogoncalves.weatherapp.repositories.WeatherTypeRepository;
import com.ua.jpedrogoncalves.weatherapp.restentities.WeatherResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Jose
 */
@RestController
@RequestMapping("/api")
public class WeatherRestControler {
    
    @Autowired 
    private WeatherLocationRepository weather_location_repo;
    @Autowired
    private LocationRepository location_repo;
    @Autowired
    private WeatherTypeRepository weather_type_repo;
    
    private WeatherResponse EMPTY_RESPONSE = new WeatherResponse();
    private Calendar lastupdate;
    
    @RequestMapping("/weather/{locationid}")
    public WeatherResponse getWeatherByLocation(@PathVariable("locationid") long locationid) {
        update();
        Location location;
        WeatherType weather_type;
        WeatherLocation weather;
        WeatherResponse response;

        Optional<Location> optional_location = location_repo.findById(locationid);

        if (!optional_location.isPresent()) {
            return EMPTY_RESPONSE;
        }
        
        location = optional_location.get();

        Optional<WeatherLocation> optional_weather = weather_location_repo.findById(location.getId());
        
        if (!optional_weather.isPresent()) {
            return EMPTY_RESPONSE;
        }
        
        weather = optional_weather.get();
        
        Optional<WeatherType> optional_weather_type = weather_type_repo.findById(weather.getWeather());
        
        if (!optional_weather_type.isPresent()) {
            return EMPTY_RESPONSE;
        }
        
        weather_type = optional_weather_type.get();
        
        response = new WeatherResponse(location.getLocation(), weather_type.getDesc(), weather.getMin_temperature(), weather.getMax_temperature());
        
        return response;
    }
    
    @RequestMapping("/weather/default")
    public WeatherResponse getWeatherForLisboa() {
        update();
        Location location;
        WeatherType weather_type;
        WeatherLocation weather;
        WeatherResponse response;

        Optional<Location> optional_location = location_repo.findByLocation("Lisboa");

        if (!optional_location.isPresent()) {
            return EMPTY_RESPONSE;
        }

        
        location = optional_location.get();

        Optional<WeatherLocation> optional_weather = weather_location_repo.findById(location.getId());
        
        if (!optional_weather.isPresent()) {
            return EMPTY_RESPONSE;
        }
        
        
        weather = optional_weather.get();
        
        Optional<WeatherType> optional_weather_type = weather_type_repo.findById(weather.getWeather());
        
        if (!optional_weather_type.isPresent()) {
            return EMPTY_RESPONSE;
        }
        
        weather_type = optional_weather_type.get();
        
        response = new WeatherResponse(location.getLocation(), weather_type.getDesc(), weather.getMin_temperature(), weather.getMax_temperature());

        return response;
    }
    
    @RequestMapping("/weather/locations")
    public ArrayList<Location> getLocations() {
        update();
        ArrayList<Location> response = new ArrayList<>();
        Iterable<Location> iterator = location_repo.findAll();
        
        for(Location location : iterator) {
            response.add(location);
        }
        
        return response;
    }
    
    @RequestMapping("/weather/types")
    public ArrayList<WeatherType> getTypes() {
        update();
        ArrayList<WeatherType> response = new ArrayList<>();
        Iterable<WeatherType> iterator = weather_type_repo.findAll();
        
        for(WeatherType weather_type : iterator) {
            response.add(weather_type);
        }
        
        return response;
    }
    
    @RequestMapping("/weather")
    public ArrayList<WeatherLocation> getAll() {
        update();
        ArrayList<WeatherLocation> response = new ArrayList<>();
        Iterable<WeatherLocation> iterator = weather_location_repo.findAll();
        
        for(WeatherLocation weather_location : iterator) {
            response.add(weather_location);
        }
        
        return response;
    }
    
    private void update() {
        //Check if a day has passed or not.
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);
        //Update the data if a day has passed. Otherwise do nothing.
        if(lastupdate == null || lastupdate.get(Calendar.YEAR) < year || lastupdate.get(Calendar.MONTH) < month || lastupdate.get(Calendar.DAY_OF_MONTH) < day) {
            
            updateLocations();
            updateWeatherTypes();
            updateWeather();
            
            lastupdate = today;
        } else {
            System.out.println("Info is up to date!");
        }
    }
    
    private void updateLocations() {
        
        RestTemplate template = new RestTemplate();
        ArrayList<LinkedHashMap<String,Object>> locations = (ArrayList<LinkedHashMap<String,Object>>) template.getForObject("http://api.ipma.pt/open-data/distrits-islands.json", RestDataHolder.class).get("data");
        
        for(LinkedHashMap<String,Object> location_data : locations) {
            long location_id = new Long((int) location_data.get("globalIdLocal")); 
            Location location = new Location( location_id, (String) location_data.get("local"));
            location_repo.save(location);
        }
    }
    
    private void updateWeatherTypes() {
        RestTemplate template = new RestTemplate();
        ArrayList<LinkedHashMap<String,Object>> weather_types = (ArrayList<LinkedHashMap<String,Object>>) template.getForObject("http://api.ipma.pt/open-data/weather-type-classe.json", RestDataHolder.class).get("data");
        
        for(LinkedHashMap<String,Object> weather_type_data : weather_types) {
            WeatherType weather_type = new WeatherType((int) weather_type_data.get("idWeatherType"), (String) weather_type_data.get("descIdWeatherTypePT"));
            weather_type_repo.save(weather_type);
        }
        
    }
    
    private void updateWeather() {
        RestTemplate template = new RestTemplate();
        ArrayList<LinkedHashMap<String,Object>> weathers = (ArrayList<LinkedHashMap<String,Object>>) template.getForObject("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/hp-daily-forecast-day0.json", RestDataHolder.class).get("data");
        
        for(LinkedHashMap<String,Object> weathers_data : weathers) {
            int idWeatherType = (int) weathers_data.get("idWeatherType");
            long globalIdLocal = new Long((int) weathers_data.get("globalIdLocal"));
            int min_temperature = (int) weathers_data.get("tMin");
            int max_temperature = (int) weathers_data.get("tMax");
            
            WeatherLocation weather_location = new WeatherLocation(globalIdLocal, idWeatherType, min_temperature, max_temperature);
            weather_location_repo.save(weather_location);
        }
        
    }
}
