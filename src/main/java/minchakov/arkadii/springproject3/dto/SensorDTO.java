package minchakov.arkadii.springproject3.dto;

import jakarta.validation.constraints.NotBlank;
import minchakov.arkadii.springproject3.model.Sensor;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

public class SensorDTO {

    @NotBlank(message = "'name' shouldn't be empty")
    @Length(min = 3, max = 30, message = "'name' length should be between 3 and 30 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SensorDTO{" + "name='" + name + '\'' + '}';
    }

    public Sensor toSensor(ModelMapper modelMapper) {
        var sensor = modelMapper.map(this, Sensor.class);
        sensor.setCreatedAt(LocalDateTime.now());
        return sensor;
    }
}
