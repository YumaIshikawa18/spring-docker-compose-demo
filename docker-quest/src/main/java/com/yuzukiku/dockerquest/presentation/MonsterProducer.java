package com.yuzukiku.dockerquest.presentation;

import com.yuzukiku.dockerquest.application.MonsterService;
import com.yuzukiku.dockerquest.domain.entity.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MonsterProducer {

    private static final Logger logger = LoggerFactory.getLogger(MonsterProducer.class);

    private final KafkaTemplate<String, Monster> kafkaTemplate;
    private final MonsterService monsterService;

    public MonsterProducer(KafkaTemplate<String, Monster> kafkaTemplate, MonsterService monsterService) {
        this.kafkaTemplate = kafkaTemplate;
        this.monsterService = monsterService;
    }

    @Scheduled(fixedRate = 1000)
    public void send() {
        Monster monster = monsterService.generateRandomMonster();
        kafkaTemplate.send("monster-topic", monster);
        logger.debug("Generated and sent monster: {}", monster.monsters().name());
    }
}
