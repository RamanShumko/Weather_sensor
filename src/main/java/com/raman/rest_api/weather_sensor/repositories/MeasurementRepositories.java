package com.raman.rest_api.weather_sensor.repositories;

import com.raman.rest_api.weather_sensor.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepositories extends JpaRepository<Measurement, Integer> {

    List<Measurement> getMeasurementsByRainingIsTrue();

}
