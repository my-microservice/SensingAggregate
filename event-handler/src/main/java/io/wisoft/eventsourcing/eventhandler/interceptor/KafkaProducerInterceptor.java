package io.wisoft.eventsourcing.eventhandler.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component

public class KafkaProducerInterceptor implements ProducerInterceptor<String, String> {

    private final Logger LOG = LoggerFactory.getLogger(KafkaProducerInterceptor.class);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        LOG.info("Send: {}", record);
        record.headers().headers("kafka_correlationId").forEach(header -> {
            String key = header.key();
            int correlationId = Arrays.hashCode(header.value());
            LOG.info("Produce {}: {}", key, correlationId);
        });
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        LOG.info("Topic '{}' Ack: Partition {}-Offset {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
    }

    @Override
    public void close() {
        LOG.info("Close");
    }

    @Override
    public void configure(Map<String, ?> configs) {
        LOG.info("Config: {}", configs);
    }
}
