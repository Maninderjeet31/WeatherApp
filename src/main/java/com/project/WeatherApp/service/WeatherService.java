package com.project.WeatherApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    public Object getWeather(float lat, float lon) throws JsonProcessingException;
}
