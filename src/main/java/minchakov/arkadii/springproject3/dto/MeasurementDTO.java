package minchakov.arkadii.springproject3.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import minchakov.arkadii.springproject3.model.Measurement;
import org.modelmapper.ModelMapper;

public class MeasurementDTO {

    @Min(value = -100, message = "'value' shouldn't be less than -100")
    @Max(value = 100, message = "'value' shouldn't be greater than 100")
    @NotNull(message = "'value' shouldn't be empty")
    private Double value;

    @NotNull(message = "'raining' should not be empty")
    private Boolean raining;

    @NotNull(message = "'sensor' should not be empty")
    @Valid
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" + "value=" + value + ", raining=" + raining + ", sensor=" + sensor + '}';
    }

    public Measurement toMeasurement(ModelMapper modelMapper) {
        return modelMapper.map(this, Measurement.class);
    }
}
