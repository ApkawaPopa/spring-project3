package minchakov.arkadii.springproject3.service;

import minchakov.arkadii.springproject3.dto.MeasurementDTO;
import minchakov.arkadii.springproject3.dto.SensorDTO;
import minchakov.arkadii.springproject3.exception.MeasurementNotCreatedException;
import minchakov.arkadii.springproject3.model.Measurement;
import minchakov.arkadii.springproject3.model.Sensor;
import minchakov.arkadii.springproject3.repository.MeasurementsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService,
        ModelMapper modelMapper
    ) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addMeasurement(MeasurementDTO measurementDTO) {
        var sensorName = measurementDTO.getSensor().getName();

        var sensorOpt = sensorsService.findByName(sensorName);

        if (sensorOpt.isEmpty()) {
            throw new MeasurementNotCreatedException(String.format("Sensor '%s' doesn't exist", sensorName));
        }

        var sensor = sensorOpt.get();

        save(enrichMeasurement(measurementDTO.toMeasurement(modelMapper), sensor));
    }

    @Transactional
    public void save(Measurement measurement) {
        measurementsRepository.save(measurement);
    }

    public Measurement enrichMeasurement(Measurement measurement, Sensor sensor) {
        measurement.setSensor(sensor);
        measurement.setCreatedAt(LocalDateTime.now());
        return measurement;
    }

    public List<MeasurementDTO> getAll() {
        return measurementsRepository.findAll()
                                     .stream()
                                     .map(m -> {
                                         var measurementDTO = new MeasurementDTO();
                                         measurementDTO.setValue(m.getValue());
                                         measurementDTO.setRaining(m.isRaining());

                                         var sensorDTO = new SensorDTO();
                                         sensorDTO.setName(m.getSensor().getName());
                                         measurementDTO.setSensor(sensorDTO);

                                         return measurementDTO;
                                     })
                                     .toList();
    }

    public int getRainyDaysCount() {
        return measurementsRepository.countRainyDays();
    }
}
