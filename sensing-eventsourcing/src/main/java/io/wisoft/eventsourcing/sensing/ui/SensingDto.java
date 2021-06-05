package io.wisoft.eventsourcing.sensing.ui;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class SensingDto {

    @Getter
    @Setter
    public static class SensingRegister {

        @NotNull
        private UUID sensorId;

        @NotNull
        private UUID actuatorId;

        @NotNull
        @Size(min = 2, max = 50)
        private String actuatingValue;

        @NotNull
        private HashMap<String, String> sensingValue;

        @NotNull
        private HashMap<String, String> environmentValue;

    }

    @Getter
    @Setter
    public static class SensingResponse {

        private UUID sensingId;
        private UUID sensorId;
        private LocalDateTime sensingTime;
        private HashMap<String, String> sensingValue;
        private HashMap<String, String> environmentValue;

    }

    @Getter
    @Setter
    public static class SensingCreation {

        @NotNull
        private UUID sensingId;

        @NotNull
        private UUID sensorId;

        @NotNull
        private LocalDateTime sensingTime;

        @NotNull
        private HashMap<String, String> sensingValue;

        @NotNull
        private HashMap<String, String> environmentValue;
    }

}
