package br.com.d2s.service.customer.publisher;

import br.com.d2s.service.customer.dao.OutboxEventDao;
import br.com.d2s.service.customer.model.OutboxEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OutboxPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(OutboxPublisher.class);

    private final OutboxEventDao outboxEventDao;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final int batchSize;
    //private final int maximumAttempts;
    private final long sendTimeoutSeconds;

    public OutboxPublisher(
            OutboxEventDao outboxEventDao,
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${outbox.publisher.batch-size:50}")
            int batchSize,
            @Value("${outbox.publisher.maximum-attempts:20}")
            int maximumAttempts,
            @Value("${outbox.publisher.send-timeout-seconds:10}")
            long sendTimeoutSeconds
    ) {
        this.outboxEventDao = outboxEventDao;
        this.kafkaTemplate = kafkaTemplate;
        this.batchSize = batchSize;
        //this.maximumAttempts = maximumAttempts;
        this.sendTimeoutSeconds = sendTimeoutSeconds;
    }

    @Scheduled(
            fixedDelayString = "${outbox.publisher.fixed-delay:1000}"
    )
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> events =
                outboxEventDao.findPendingEvents(Pageable.ofSize(batchSize));
        IO.println("Publishing events: " + events.size());
        for (OutboxEvent event : events) {
            publish(event);
        }
    }

    private void publish(OutboxEvent event) {
        try {
            IO.println("Publishing event: " + event.getPayload());
            kafkaTemplate.send(
                    event.getTopic(),
                    event.getEventKey(),
                    event.getPayload()
            ).get(sendTimeoutSeconds, TimeUnit.SECONDS);

            event.markAsPublished();

            log.info(
                    "Outbox event published: eventId={}, eventType={}, topic={}",
                    event.getId(),
                    event.getEventType(),
                    event.getTopic()
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            //registerFailure(event, exception);
        } catch (Exception exception) {
            //registerFailure(event, exception);
        }
    }
}