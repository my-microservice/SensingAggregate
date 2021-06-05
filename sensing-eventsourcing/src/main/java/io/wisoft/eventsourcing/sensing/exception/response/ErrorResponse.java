package io.wisoft.eventsourcing.sensing.exception.response;

import lombok.*;

@Builder
@Getter
public class ErrorResponse {

    private final String message;
    private final String title;

}
