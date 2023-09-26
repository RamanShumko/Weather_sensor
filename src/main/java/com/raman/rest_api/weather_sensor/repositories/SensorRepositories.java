package com.raman.rest_api.weather_sensor.repositories;

import com.raman.rest_api.weather_sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepositories extends JpaRepository<Sensor, Integer> {

    Sensor findSensorByName(String name);
}
