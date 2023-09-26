package com.raman.rest_api.weather_sensor.services;

import com.raman.rest_api.weather_sensor.entity.Measurement;
import com.raman.rest_api.weather_sensor.entity.Sensor;
import com.raman.rest_api.weather_sensor.repositories.MeasurementRepositories;
import com.raman.rest_api.weather_sensor.repositories.SensorRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MeasurementServiceImplementation implements MeasurementService{

    private final MeasurementRepositories measurementRepositories;

    private final SensorRepositories sensorRepositories;

    @Autowired
    public MeasurementServiceImplementation(MeasurementRepositories measurementRepositories, SensorRepositories sensorRepositories) {
        this.measurementRepositories = measurementRepositories;
        this.sensorRepositories = sensorRepositories;
    }

    @Override
    public List<Measurement> findAll() {
        return measurementRepositories.findAll();
    }

    @Override
    public void save(Measurement measurement) {
        measurement.setDateOfMeasurement(new Date());
        Sensor sensor = measurement.getSensor();
        sensor.setId(sensorRepositories.findSensorByName(sensor.getName()).getId());
        measurementRepositories.save(measurement);
    }

    @Override
    public Integer getCountRainingDays() {
        return measurementRepositories.getMeasurementsByRainingIsTrue().size();
    }
}
