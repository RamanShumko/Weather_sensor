package com.raman.rest_api.weather_sensor.controllers;

import com.raman.rest_api.weather_sensor.dto.SensorDTO;
import com.raman.rest_api.weather_sensor.entity.Sensor;
import com.raman.rest_api.weather_sensor.services.SensorService;
import com.raman.rest_api.weather_sensor.util.SensorErrorResponse;
import com.raman.rest_api.weather_sensor.util.SensorNotCreateException;
import com.raman.rest_api.weather_sensor.util.SensorNotFoundException;
import com.raman.rest_api.weather_sensor.validation.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorValidator.validate(convertSensorDTOToSensor(sensorDTO), bindingResult);
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errors = new ArrayList<>(bindingResult.getFieldErrors());
            for(FieldError error : errors) {
                stringBuilder.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new SensorNotCreateException(stringBuilder.toString());
        }

        sensorService.save(convertSensorDTOToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<SensorDTO> showAllSensors() {
        if(sensorService.getAllSensor().isEmpty()){
            throw new SensorNotFoundException();
        }

        return sensorService.getAllSensor()
                .stream()
                .map(this::convertSensorToSensorDTO)
                .collect(Collectors.toList());
    }

    private Sensor convertSensorDTOToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertSensorToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse("Not found sensor");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreateException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
