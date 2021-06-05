package io.wisoft.eventsourcing.sensing.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@ToString
@Getter
public class AutoControlDetectCommand {

    @TargetAggregateIdentifier
    private UUID sensingId;
    private UUID sensorId;
    private UUID actuatorId;
    private String actuatorName;
    private String actuatingValue;
    private LocalDateTime sensingTime;
    private HashMap<String, String> sensingValue;
    private HashMap<String, String> environmentValue;

}
