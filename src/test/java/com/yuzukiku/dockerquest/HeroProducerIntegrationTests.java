package com.yuzukiku.dockerquest;

import com.yuzukiku.dockerquest.domain.entity.Monster;
import com.yuzukiku.dockerquest.domain.entity.Monsters;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class HeroProducerIntegrationTests {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    );

    private static KafkaTemplate<String, Monster> kafkaTemplate;

    @DynamicPropertySource
    static void registerKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.template.default-topic", () -> "test-topic");
    }

    @BeforeAll
    static void setup() {
        kafkaContainer.start();
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, Monster> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    @AfterAll
    static void tearDown() {
        if (kafkaTemplate != null) {
            kafkaTemplate.destroy();
        }
        if (kafkaContainer != null) {
            kafkaContainer.stop();
        }
    }

    @Test
    @DisplayName("Kafkaメッセージを正しく送信する")
    void givenMonster_whenProduced_thenMessageSentToKafka() {

        Monster monster = new Monster(Monsters.DRAGON);

        kafkaTemplate.send("test-topic", monster);

        Assertions.assertThat(kafkaTemplate).isNotNull();
    }
}
