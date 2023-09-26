package com.raman.rest_api.weather_sensor.dto;

import jakarta.validation.constraints.*;

public class MeasurementDTO {

    @NotNull(message = "Value should not be empty")
    @Min(value = -100, message = "Value should be greater than -101")
    @Max(value = 100, message = "Value should be less than 101")
    private Double value;

    @NotNull(message = "Field should not be empty")
    private Boolean raining;

    @NotNull(message = "Sensor should not be empty")
    private SensorDTO sensorDTO;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensorDTO;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensorDTO = sensor;
    }
}
