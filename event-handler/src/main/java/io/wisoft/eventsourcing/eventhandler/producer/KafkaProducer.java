package io.wisoft.eventsourcing.eventhandler.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.wisoft.eventsourcing.eventhandler.dto.SensingMessage;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTemplate<String, Object> pojoKafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate,
                       @Qualifier("pojoKafkaTemplate") KafkaTemplate<String, Object> pojoKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.pojoKafkaTemplate = pojoKafkaTemplate;
    }

    public void produceForEvent(String topic, SensingMessage msg) throws JsonProcessingException {
        pojoKafkaTemplate.send(topic, msg).addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                throwable.getStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                String topic = metadata.topic();
                long offset = metadata.offset();
                int partition = metadata.partition();
                LOG.info("Pojo Produce result: Topic {}, Partition {}, Offset {}", topic, partition, offset);
            }
        });
    }
}
