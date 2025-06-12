package com.project.WeatherApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.WeatherApp.resource.WeatherErrorResponseResource;
import com.project.WeatherApp.resource.WeatherResponseResource;
import com.project.WeatherApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Object getWeather(float lat, float lon) throws JsonProcessingException {

        String query = "https://api.open-meteo.com/v1/forecast?latitude=" +lat+ "&longitude=" + lon + "&current=temperature_2m,rain,showers,snowfall,wind_speed_10m,is_day&forecast_days=1&timezone=auto";
        String jsonResponse = restTemplate.getForObject(query, String.class);
        Object response;

        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        if (rootNode.has("error") && rootNode.get("error").asBoolean()){
            response = objectMapper.treeToValue(rootNode, WeatherErrorResponseResource.class);
        } else {
            response = objectMapper.treeToValue(rootNode, WeatherResponseResource.class);
        }

        return response;
    }
}
