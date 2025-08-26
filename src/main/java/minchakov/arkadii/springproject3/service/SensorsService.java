package minchakov.arkadii.springproject3.service;

import minchakov.arkadii.springproject3.dto.SensorDTO;
import minchakov.arkadii.springproject3.exception.SensorNotCreatedException;
import minchakov.arkadii.springproject3.model.Sensor;
import minchakov.arkadii.springproject3.repository.SensorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void registerSensor(SensorDTO sensorDTO) {
        if (isPresentByName(sensorDTO.getName())) {
            throw new SensorNotCreatedException(
                String.format("Sensor with name '%s' already exists", sensorDTO.getName())
            );
        }

        save(sensorDTO.toSensor(modelMapper));
    }

    public boolean isPresentByName(String sensorName) {
        return sensorsRepository.findByName(sensorName).isPresent();
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String sensorName) {
        return sensorsRepository.findByName(sensorName);
    }
}
