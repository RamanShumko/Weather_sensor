package com.raman.rest_api.weather_sensor.controllers;

import com.raman.rest_api.weather_sensor.dto.MeasurementDTO;
import com.raman.rest_api.weather_sensor.entity.Measurement;
import com.raman.rest_api.weather_sensor.services.MeasurementService;
import com.raman.rest_api.weather_sensor.util.MeasurementErrorResponse;
import com.raman.rest_api.weather_sensor.util.MeasurementNotCreateException;
import com.raman.rest_api.weather_sensor.util.MeasurementNotFoundException;
import com.raman.rest_api.weather_sensor.validation.MeasurementValidator;
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
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 ModelMapper modelMapper,
                                 MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public List<MeasurementDTO> showAll() {
        if (measurementService.findAll().isEmpty()){
            throw new MeasurementNotFoundException();
        }

        return measurementService.findAll()
                .stream()
                .map(this::convertMeasurementToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        measurementValidator.validate(convertMeasurementDTOToMeasurement(measurementDTO).getSensor(), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errors = new ArrayList<>(bindingResult.getFieldErrors());

            for (FieldError error : errors) {
                stringBuilder.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new MeasurementNotCreateException(stringBuilder.toString());
        }

        measurementService.save(convertMeasurementDTOToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Integer getCountRainingDays() {
        return measurementService.getCountRainingDays();
    }

    private MeasurementDTO convertMeasurementToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertMeasurementDTOToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse("Not found measurement");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreateException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
