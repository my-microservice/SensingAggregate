package io.wisoft.eventsourcing.sensing.service;

import io.wisoft.eventsourcing.sensing.ui.SensingDto;

import java.util.concurrent.CompletableFuture;

public interface SensingService {

    CompletableFuture<Object> creation(final SensingDto.SensingCreation sensingDto);
}
