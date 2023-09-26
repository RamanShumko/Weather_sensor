package com.raman.rest_api.weather_sensor.services;

import com.raman.rest_api.weather_sensor.entity.Sensor;
import com.raman.rest_api.weather_sensor.repositories.SensorRepositories;
import com.raman.rest_api.weather_sensor.util.SensorErrorResponse;
import com.raman.rest_api.weather_sensor.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SensorServiceImplementation implements SensorService{

    private final SensorRepositories sensorRepositories;

    @Autowired
    public SensorServiceImplementation(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }

    @Override
    public void save(Sensor sensor) {
        sensorRepositories.save(sensor);
    }

    @Override
    public List<Sensor> getAllSensor() {
        return sensorRepositories.findAll();
    }

    @Override
    public Sensor getSensorByName(String name) {
        return sensorRepositories.findSensorByName(name);
    }
}
