package com.raman.rest_api.weather_sensor.validation;

import com.raman.rest_api.weather_sensor.entity.Measurement;
import com.raman.rest_api.weather_sensor.entity.Sensor;
import com.raman.rest_api.weather_sensor.services.MeasurementService;
import com.raman.rest_api.weather_sensor.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensor != null && sensorService.getSensorByName(sensor.getName()) == null) {
            errors.rejectValue("sensor", "", "This sensor does not exist");
        }
    }
}
