package io.wisoft.eventsourcing.sensing.service;

import io.wisoft.eventsourcing.sensing.exception.ActuatorNotFoundException;
import io.wisoft.eventsourcing.sensing.exception.SensorNotFoundException;
import io.wisoft.eventsourcing.sensing.ui.SensingDto;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class SensingEventServiceUnitTest {

    @InjectMocks
    private SensingServiceImpl sensingServiceImpl;

    private SensingDto.SensingRegister registerSensingDto;

    HashMap<String, String> sensingValue;
    HashMap<String, String> environmentVariable;


    @BeforeEach
    public void init() {
        sensingValue = new HashMap<>();
        environmentVariable = new HashMap<>();

        sensingValue.put("dustValue", "40.0");
        environmentVariable.put("temperature", "30.0");

        registerSensingDto = new SensingDto.SensingRegister();

        registerSensingDto.setSensorId(UUID.fromString("4b376620-7f5d-11eb-9881-0800200c9a66"));
        registerSensingDto.setActuatorId(UUID.fromString("798c0e40-7f5d-11eb-9881-0800200c9a66"));
        registerSensingDto.setActuatingValue("START");
        registerSensingDto.setSensingValue(sensingValue);
        registerSensingDto.setEnvironmentValue(environmentVariable);

    }


    @Test
    @DisplayName("이벤트를 등록하기 전에 센서의 유무를 확인하고 없으면 SensorNotFoundException을 반환합니다.")
    void givenRegisterSensingDtoWhenSensingEventRegisterThenSensorNotFoundException() throws ParseException {
        // given

        registerSensingDto = new SensingDto.SensingRegister();

        registerSensingDto.setSensorId(UUID.fromString("4b376620-7f5d-11eb-9881-0800200c9a67"));
        registerSensingDto.setActuatorId(UUID.fromString("798c0e40-7f5d-11eb-9881-0800200c9a66"));
        registerSensingDto.setActuatingValue("START");
        registerSensingDto.setSensingValue(sensingValue);
        registerSensingDto.setEnvironmentValue(environmentVariable);


        SensorNotFoundException thrown = assertThrows(
                SensorNotFoundException.class,
                () -> findBySensorId(registerSensingDto.getSensorId()));

        Assertions.assertTrue(thrown.getMessage().contains("not found!"));
    }

    @Test
    @DisplayName("이벤트를 등록하기 전에 엑추에이터의 유무를 확인하고 없으면 ActuatorNotFoundException을 반환합니다.")
    void givenRegisterSensingDtoWhenSensingEventRegisterThenActuatorNotFoundException() throws ParseException {
        // given

        registerSensingDto = new SensingDto.SensingRegister();

        registerSensingDto.setSensorId(UUID.fromString("4b376620-7f5d-11eb-9881-0800200c9a67"));
        registerSensingDto.setActuatorId(UUID.fromString("798c0e40-7f5d-11eb-9881-0800200c9a66"));
        registerSensingDto.setActuatingValue("START");
        registerSensingDto.setSensingValue(sensingValue);
        registerSensingDto.setEnvironmentValue(environmentVariable);


        // when
        ActuatorNotFoundException thrown = assertThrows(
                ActuatorNotFoundException.class,
                () -> findByActuatorId(registerSensingDto.getSensorId()));

        // then

        assertThat(registerSensingDto.getActuatorId()).isEqualTo(UUID.fromString("798c0e40-7f5d-11eb-9881-0800200c9a66"));
        Assertions.assertTrue(thrown.getMessage().contains("not found!"));
    }


    String findBySensorId(UUID sensorId) throws SensorNotFoundException {
        throw new SensorNotFoundException(sensorId + "not found!");
    }

    String findByActuatorId(UUID actuatorId) throws ActuatorNotFoundException {
        throw new ActuatorNotFoundException(actuatorId + "not found!");
    }
}