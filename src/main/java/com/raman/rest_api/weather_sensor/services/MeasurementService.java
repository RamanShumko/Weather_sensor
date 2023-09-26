package com.raman.rest_api.weather_sensor.services;

import com.raman.rest_api.weather_sensor.entity.Measurement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MeasurementService {

    public List<Measurement> findAll();

    public void save(Measurement measurement);

    public Integer getCountRainingDays();
}
