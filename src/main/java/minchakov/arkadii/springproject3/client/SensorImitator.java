package minchakov.arkadii.springproject3.client;

import minchakov.arkadii.springproject3.dto.MeasurementDTO;
import minchakov.arkadii.springproject3.dto.SensorDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

public class SensorImitator {
    public static void main(String[] args) {
        var restTemplate = new RestTemplate();

        var sensorDTO = new SensorDTO();
        sensorDTO.setName("firstSensor");


        try {
            System.out.println(restTemplate.postForObject(
                "http://localhost:8080/sensors/registration",
                sensorDTO,
                String.class
            ));
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println(e.getResponseBodyAsString());
        }

        var random = new Random();
        for (int i = 0; i < 1000; i++) {
            var measurementDTO = new MeasurementDTO();
            measurementDTO.setValue(random.nextDouble(-100, 100));
            measurementDTO.setRaining(random.nextBoolean());
            measurementDTO.setSensor(sensorDTO);

            try {
                System.out.println(restTemplate.postForObject(
                    "http://localhost:8080/measurements/add",
                    measurementDTO,
                    String.class
                ));
            } catch (HttpClientErrorException.BadRequest e) {
                System.out.println(e.getResponseBodyAsString());
            }
        }

        var addedMeasurements = restTemplate.exchange(
            "http://localhost:8080/measurements",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<MeasurementDTO>>() {}
        ).getBody();

        assert addedMeasurements != null;
        for (var i : addedMeasurements) {
            System.out.println(i);
        }
    }
}
