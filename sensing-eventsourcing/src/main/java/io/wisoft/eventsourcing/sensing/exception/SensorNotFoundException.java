package io.wisoft.eventsourcing.sensing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SensorNotFoundException extends RuntimeException {

    @Getter
    final String information;

}
