package io.wisoft.eventsourcing.sensing.otherservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.wisoft.eventsourcing.sensing.exception.SensorNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/*
Sensor Domain Hystrix 체크
 */
@Slf4j
@Service
public class SensorService {

    @Value("${multiplicationHost}")
    private String API_GATE_HOST;
    private RestTemplate restTemplate;

    public SensorService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3")
            },
            fallbackMethod = "checkExistSensorNoResponseSensor")
    public void checkExistSensor(final UUID sensorId) {
        try {
            System.out.println("API_GATE_HOST" + API_GATE_HOST);
            restTemplate.getForEntity(API_GATE_HOST + "/sensors/{id}", Object.class, sensorId);
        } catch (HttpClientErrorException e) {
            throw new SensorNotFoundException(String.valueOf(sensorId));
        }
    }

    private void checkExistSensorNoResponseSensor(final UUID sensorId) {
        log.error(sensorId + " : No Response SensorId");
    }


}
