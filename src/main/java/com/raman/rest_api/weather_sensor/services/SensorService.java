package com.raman.rest_api.weather_sensor.services;

import com.raman.rest_api.weather_sensor.entity.Measurement;
import com.raman.rest_api.weather_sensor.entity.Sensor;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SensorService {

    public void save(Sensor sensor);

    public List<Sensor> getAllSensor();

    public Sensor getSensorByName(String name);
}
