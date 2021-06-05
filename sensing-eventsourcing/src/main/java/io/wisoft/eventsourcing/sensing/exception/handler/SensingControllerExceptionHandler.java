package io.wisoft.eventsourcing.sensing.exception.handler;

import io.wisoft.eventsourcing.sensing.exception.ActuatorNotFoundException;
import io.wisoft.eventsourcing.sensing.exception.SensorNotFoundException;
import io.wisoft.eventsourcing.sensing.exception.response.BindingErrorResponse;
import io.wisoft.eventsourcing.sensing.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SensingControllerExceptionHandler {

    @ExceptionHandler(ActuatorNotFoundException.class)
    public ResponseEntity<ErrorResponse> actuatorNotFoundExceptionHandler(final ActuatorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .title("Actuator Not Found Exception")
                        .message("입력하신 " + e.getInformation() + "는 존재하지 않는 액추에이터입니다.")
                        .build()
        );
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<ErrorResponse> sensorNotFoundExceptionHandler(final SensorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .title("Sensor Not Found Exception")
                                .message("입력하신 " + e.getInformation() + "는 존재하지 않는 센서입니다.")
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BindingErrorResponse> MethodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException e) {
        final BindingErrorResponse errorResponse = new BindingErrorResponse();
        errorResponse.setTitle("Binding Exception");
        errorResponse.setMessage("입력하신 정보가 유효하지 않습니다.");
        errorResponse.setErrors(e.getBindingResult().getFieldErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
