package minchakov.arkadii.springproject3.controller;

import jakarta.validation.Valid;
import minchakov.arkadii.springproject3.dto.SensorDTO;
import minchakov.arkadii.springproject3.exception.SensorNotCreatedException;
import minchakov.arkadii.springproject3.response.ErrorResponse;
import minchakov.arkadii.springproject3.service.SensorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;

    public SensorsController(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new SensorNotCreatedException(
                errors
                    .getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + " - " + e.getDefaultMessage())
                    .collect(Collectors.joining("; "))
            );
        }

        sensorsService.registerSensor(sensorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleSensorNotCreatedException(SensorNotCreatedException e) {
        var response = new ErrorResponse(
            e.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
