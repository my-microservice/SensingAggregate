package io.wisoft.eventsourcing.sensing.configure;

import io.wisoft.eventsourcing.sensing.domain.SensingAggregate;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfiguration {

    @Bean
    SimpleCommandBus commandBus(TransactionManager transactionManager){
        return  SimpleCommandBus.builder().transactionManager(transactionManager).build();
    }

    @Bean
    public AggregateFactory<SensingAggregate> aggregateFactory() {
        return new GenericAggregateFactory<>(SensingAggregate.class);
    }

    @Bean
    public Snapshotter snapshotter(EventStore eventStore, TransactionManager transactionManager) {
        return AggregateSnapshotter
                .builder()
                .eventStore(eventStore)
                .aggregateFactories(aggregateFactory())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(EventStore eventStore, TransactionManager transactionManager) {
        final int SNAPSHOT_THRESHOLD = 100;
        return new EventCountSnapshotTriggerDefinition(snapshotter(eventStore, transactionManager), SNAPSHOT_THRESHOLD);
    }

    @Bean
    public Repository<SensingAggregate> accountAggregateRepository(EventStore eventStore, SnapshotTriggerDefinition snapshotTriggerDefinition) {
        return EventSourcingRepository
                .builder(SensingAggregate.class)
                .eventStore(eventStore)
                .snapshotTriggerDefinition(snapshotTriggerDefinition)
                .build();

    }
}
