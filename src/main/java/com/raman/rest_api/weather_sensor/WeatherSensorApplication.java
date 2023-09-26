package com.raman.rest_api.weather_sensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherSensorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherSensorApplication.class, args);
        System.out.println("Hello");
    }

    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }

}
