package io.wisoft.eventsourcing.eventhandler.projection;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wisoft.eventsourcing.eventhandler.dto.SensingMessage;
import io.wisoft.eventsourcing.eventhandler.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;
import sensing.SensingCreationEvent;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class NosqlSensingProjection {

    private final KafkaProducer kafkaProducer;

    @EventHandler
    protected void on(SensingCreationEvent event, @Timestamp Instant instant) throws JsonProcessingException {
        log.debug("projecting {}, timestamp {}", event, instant);
        kafkaProducer.produceForEvent("sensing-topic1",
                new SensingMessage(
                        event.getSensingId(),
                        event.getSensorId(),
                        event.getSensingTime(),
                        event.getSensingValue(),
                        event.getEnvironmentValue()
                ));

    }

}
