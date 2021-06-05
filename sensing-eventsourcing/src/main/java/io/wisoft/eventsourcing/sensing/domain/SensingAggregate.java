package io.wisoft.eventsourcing.sensing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sensing.SensingCreationEvent;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SensingAggregate {

    private final Logger LOG = LoggerFactory.getLogger(SensingAggregate.class);

    @AggregateIdentifier
    private UUID sensingId;
    private UUID sensorId;
    private LocalDateTime sensingTime;
    private HashMap<String, String> sensingValue;
    private HashMap<String, String> environmentValue;


    @CommandHandler
    public SensingAggregate(final SensingCreationCommand command) {
        LOG.info("handling{}: ", command);
        apply(new SensingCreationEvent(
                command.getSensingId(),
                command.getSensorId(),
                command.getSensingTime(),
                command.getSensingValue(),
                command.getEnvironmentValue()));
    }

    @EventSourcingHandler
    protected void SensingCreationAggregate(SensingCreationEvent event) {
        this.sensingId = event.getSensingId();
        this.sensorId = event.getSensorId();
        this.sensingTime = event.getSensingTime();
        this.sensingValue = event.getSensingValue();
        this.environmentValue = event.getEnvironmentValue();
    }
}
