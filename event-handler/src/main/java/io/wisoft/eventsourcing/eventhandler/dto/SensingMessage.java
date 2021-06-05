package io.wisoft.eventsourcing.eventhandler.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class SensingMessage implements Serializable {

    private final UUID sensingId;
    private final UUID sensorId;
    private final LocalDateTime sensingTime;
    private final HashMap<String, String> sensingValue;
    private final HashMap<String, String> environmentValue;
}