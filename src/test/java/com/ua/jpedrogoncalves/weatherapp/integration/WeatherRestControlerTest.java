/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ua.jpedrogoncalves.weatherapp.integration;

import com.ua.jpedrogoncalves.weatherapp.WeatherappApplication;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Jose
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = WeatherappApplication.class)
@AutoConfigureMockMvc
public class WeatherRestControlerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Test of getWeatherByLocation method, of class WeatherRestControler.
     */
    @Test
    public void testGetWeatherByLocation() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/weather/1010500").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Aveiro"));
    }

    /**
     * Test of getWeatherForLisboa method, of class WeatherRestControler.
     */
    @Test
    public void testGetWeatherForLisboa() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/weather/default").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location").value("Lisboa"));
    }

    /**
     * Test of getLocation method, of class WeatherRestControler.
     */
    @Test
    public void testGetLocations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/weather/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value("Aveiro"));
    }

    /**
     * Test of getTypes method, of class WeatherRestControler.
     */
    @Test
    public void testGetTypes() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/weather/types").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].id").value(1));
    }

    /**
     * Test of getAll method, of class WeatherRestControler.
     */
    @Test
    public void testGetAll() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/api/weather").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location").value(1010500));
    }

}
