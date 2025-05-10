package com.tyaremenko.userservice.kafka;

import com.tyaremenko.userservice.domain.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import user.events.UserEvent;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEntity user) {
        UserEvent event = UserEvent.newBuilder()
                                   .setUserId(user.getId().toString())
                                   .setName(user.getName())
                                   .setEmail(user.getEmail())
                                   .setEventType("PATIENT_CREATED")
                                   .build();

        try {
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e) {
            log.error("Error sending PatientCreated event: {}", event);
        }
    }
}
