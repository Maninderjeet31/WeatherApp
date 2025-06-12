package com.project.WeatherApp.resource;

import com.project.WeatherApp.model.Current;
import com.project.WeatherApp.model.CurrentUnits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseResource {

    private float lat;
    private float lon;
    private String timezone;
    private String timezone_abbreviation;
    private CurrentUnits current_units;
    private Current current;
}
