package com.project.WeatherApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.WeatherApp.model.Current;
import com.project.WeatherApp.model.CurrentUnits;
import com.project.WeatherApp.resource.UserCityRequestResource;
import com.project.WeatherApp.resource.WeatherErrorResponseResource;
import com.project.WeatherApp.resource.WeatherApiResponseResource;
import com.project.WeatherApp.resource.WeatherResponseResource;
import com.project.WeatherApp.service.WeatherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/weather")
@Log4j2
public class CityWeatherController {

    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Autowired
    WeatherService weatherService;

    @PostMapping("/")
    public ResponseEntity<WeatherApiResponseResource> getWeather(
            @RequestBody @Valid UserCityRequestResource userCityRequestResource) throws JsonProcessingException {

        //logging request status for IP Address
        log.info("Weather requested for lat:{}, lon:{}", userCityRequestResource.getLat(), userCityRequestResource.getLon());

        //Fetching the response from API with IP Address
        Object response = weatherService.getWeather(userCityRequestResource.getLat(), userCityRequestResource.getLon());

        //if the response is an error
        if(response instanceof WeatherErrorResponseResource){

            //cast the response to error class
            WeatherErrorResponseResource apiResponse = (WeatherErrorResponseResource) response;

            //log user registration request status
            log.error("Weather response received as unsuccessful for lat: {}, lon: {}",
                    userCityRequestResource.getLat(), userCityRequestResource.getLon());

            //setting response values
            WeatherApiResponseResource serviceResponse = getApiResponse(false, apiResponse);

            return new ResponseEntity<>(serviceResponse,HttpStatus.NOT_FOUND);
        }
        //if the API returned with weather details
        else{

            //cast the response to error class
            WeatherResponseResource apiResponse = (WeatherResponseResource) response;

            //logging successful response from API
            log.info("Weather response received as successful for lat: {}, lon: {}",
                    userCityRequestResource.getLat(), userCityRequestResource.getLon());

            log.info("The received weather response: {}", apiResponse);

            //setting response values
            WeatherApiResponseResource serviceResponse = getApiResponse(true, apiResponse);

            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
        }
    }

    //General method - generating the response message
    private static WeatherApiResponseResource getApiResponse(boolean found, Object response) {
        if (found){
            return WeatherApiResponseResource.builder()
                    .found(true)
                    .message(Optional.ofNullable(response))
                    .build();
        } else {
            return WeatherApiResponseResource.builder()
                    .found(false)
                    .message(Optional.of(
                            WeatherResponseResource.builder()
                                    .lat(0.0f)
                                    .lon(0.0f)
                                    .timezone("N/A")
                                    .timezone_abbreviation("N/A")
                                    .current_units(CurrentUnits.builder()
                                            .time("N/A")
                                            .interval("N/A")
                                            .temperature_2m("N/A")
                                            .rain("N/A")
                                            .showers("N/A")
                                            .snowfall("N/A")
                                            .wind_speed_10m("N/A")
                                            .is_day("N/A")
                                            .build())
                                    .current(Current.builder()
                                            .time("N/A")
                                            .interval(0)
                                            .temperature_2m(0.0)
                                            .rain(0.0)
                                            .showers(0.0)
                                            .snowfall(0.0)
                                            .wind_speed_10m(0.0)
                                            .is_day(0)
                                    .build()
                    )))
                    .build();
        }
    }
}
