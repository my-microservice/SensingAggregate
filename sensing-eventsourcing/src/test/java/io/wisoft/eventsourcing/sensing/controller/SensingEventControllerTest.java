package io.wisoft.eventsourcing.sensing.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.eventsourcing.sensing.service.SensingServiceImpl;
import io.wisoft.eventsourcing.sensing.ui.SensingController;
import io.wisoft.eventsourcing.sensing.ui.SensingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SensingController.class)
public class SensingEventControllerTest {

    private static final String API_V1_BASE_URI = "/event-sensings/";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SensingServiceImpl sensingServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
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
    @DisplayName("resigterDTO를 넘겨줬을때 정상적으로 이벤트가 등록됩니다.")
    void givenRegisterWhenCreateSensingThenReturnSensingId() throws Exception {
        // given
        final CompletableFuture<SensingDto.SensingRegister> sensingRegisterCompletableFuture = CompletableFuture.completedFuture(registerSensingDto);

        // when

        Mockito
                .when(sensingServiceImpl.register(registerSensingDto))
                .thenReturn(CompletableFuture.completedFuture(registerSensingDto));

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(API_V1_BASE_URI + "/auto")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerSensingDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

}