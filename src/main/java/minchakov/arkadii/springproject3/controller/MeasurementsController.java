package minchakov.arkadii.springproject3.controller;

import jakarta.validation.Valid;
import minchakov.arkadii.springproject3.dto.MeasurementDTO;
import minchakov.arkadii.springproject3.exception.MeasurementNotCreatedException;
import minchakov.arkadii.springproject3.response.ErrorResponse;
import minchakov.arkadii.springproject3.service.MeasurementsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;

    public MeasurementsController(MeasurementsService measurementsService) {
        this.measurementsService = measurementsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(
        @RequestBody @Valid MeasurementDTO measurementDTO,
        BindingResult errors
    ) {
        if (errors.hasErrors()) {
            throw new MeasurementNotCreatedException(
                errors
                    .getFieldErrors()
                    .stream()
                    .map(e -> e.getField() + " - " + e.getDefaultMessage())
                    .collect(Collectors.joining("; "))
            );
        }

        measurementsService.addMeasurement(measurementDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<MeasurementDTO> listMeasurements() {
        return measurementsService.getAll();
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementsService.getRainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleMeasurementNotCreatedException(MeasurementNotCreatedException e) {
        var response = new ErrorResponse(
            e.getMessage(),
            System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
