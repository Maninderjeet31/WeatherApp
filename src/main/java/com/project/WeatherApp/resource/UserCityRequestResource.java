package com.project.WeatherApp.resource;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCityRequestResource {
    private UUID userUUID;
    private String name;
    private String country;
    private String city;

    @Min(value = -90, message = "Latitude must be greater than or equal to -90.")
    @Max(value = 90, message = "Latitude must be less than or equal to 90.")
    private float lat;

    @Min(value = -90, message = "Longitude must be greater than or equal to -90.")
    @Max(value = 90, message = "Longitude must be less than or equal to 90.")
    private float lon;
}
