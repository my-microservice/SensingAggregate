package io.wisoft.eventsourcing.sensing.domain;


import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import sensing.AutoControlDetectedEvent;

import java.time.LocalDateTime;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
public class SensingEventUnitTest {

    JSONObject environmentVariable;
    JSONObject dustValue;
    FixtureConfiguration<SensingAggregate> fixture;

    @BeforeEach
    public void init() {
        fixture = new AggregateTestFixture<>(SensingAggregate.class);
    }

    @Test
    @DisplayName("SensingAggregate에서 이벤트를 성공적으로 만드는지 확인합니다.")
    void givenAutoControlDetectCommandWhenSensingAggregateCreateThenAutoControlDetectedEvent() {
        // given
        environmentVariable = new JSONObject();
        dustValue = new JSONObject();

        environmentVariable.put("temperature", 30.0);
        dustValue.put("dustValue", 40.0);

        AutoControlDetectCommand command = new AutoControlDetectCommand(
                UUID.fromString("54db0f75-c190-4bc9-ad13-bfa15dd224cc"),
                UUID.fromString("2c97c948-f857-45e9-b9a7-771024ead827"),
                UUID.fromString("2e9adfef-8882-4351-9b5c-1623046134c9"),
                "A0001",
                "START",
                LocalDateTime.now(),
                dustValue,
                environmentVariable);

        fixture.given()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEvents(new AutoControlDetectedEvent(
                        command.getSensingId(),
                        command.getSensorId(),
                        command.getActuatorId(),
                        command.getActuatorName(),
                        command.getActuatingValue(),
                        command.getSensingTime(),
                        command.getSensingValue(),
                        command.getEnvironmentValue()));
    }
}