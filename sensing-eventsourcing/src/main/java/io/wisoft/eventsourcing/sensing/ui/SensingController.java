package io.wisoft.eventsourcing.sensing.ui;

import io.wisoft.eventsourcing.sensing.service.SensingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/event-sensings")
@RequiredArgsConstructor
public class SensingController {

    private final SensingServiceImpl sensingServiceImpl;

    @PostMapping("create")
    public ResponseEntity<CompletableFuture<Object>> createSensing(@RequestBody @Valid final SensingDto.SensingCreation creationDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(sensingServiceImpl.creation(creationDto));
    }
}
