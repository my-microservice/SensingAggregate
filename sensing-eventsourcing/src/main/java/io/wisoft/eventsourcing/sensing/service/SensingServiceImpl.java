package io.wisoft.eventsourcing.sensing.service;


import io.wisoft.eventsourcing.sensing.domain.SensingCreationCommand;
import io.wisoft.eventsourcing.sensing.otherservice.SensorService;
import io.wisoft.eventsourcing.sensing.ui.SensingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensingServiceImpl implements SensingService {

    private final Logger LOG = LoggerFactory.getLogger(SensingServiceImpl.class);

    @Value("${multiplicationHost}")
    private String API_GATEWAY_HOST;
    private final RestTemplate restTemplate;
    private final CommandGateway gateway;
    private final SensorService sensorService;

    @Override
    public CompletableFuture<Object> creation(SensingDto.SensingCreation sensingDto) {
        LOG.debug("creation{}: ", sensingDto);

        return gateway.send(new SensingCreationCommand(
                UUID.randomUUID(),
                sensingDto.getSensorId(),
                LocalDateTime.now(),
                sensingDto.getSensingValue(),
                sensingDto.getEnvironmentValue()
        ));
    }

}
