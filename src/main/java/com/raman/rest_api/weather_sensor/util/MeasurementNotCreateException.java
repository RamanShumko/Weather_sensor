package com.raman.rest_api.weather_sensor.util;

public class MeasurementNotCreateException extends RuntimeException{
    public MeasurementNotCreateException(String message) {
        super(message);
    }
}
