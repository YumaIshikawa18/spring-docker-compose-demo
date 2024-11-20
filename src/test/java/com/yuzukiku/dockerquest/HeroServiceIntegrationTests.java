package com.yuzukiku.dockerquest;

import com.yuzukiku.dockerquest.application.HeroService;
import com.yuzukiku.dockerquest.domain.entity.Hero;
import com.yuzukiku.dockerquest.domain.repository.HeroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@Import({HeroService.class, TestcontainersConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // デフォルトのインメモリDBを使用せず、実際のDB設定を使用
@DirtiesContext
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class}) // Kafkaの自動構成を除外
public class HeroServiceIntegrationTests {

    @Autowired
    private HeroService heroService;

    @Autowired
    private HeroRepository heroRepo;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    @DisplayName("PostgreSQLを使用して新たな勇者が誕生される")
    void givenNoHeroInDatabase_whenGetHero_thenNewHeroIsCreatedAndSaved() {
        Hero hero = heroService.getHero(1);
        hero.setLevel(1);
        hero.setExperience(0);

        Assertions.assertThat(hero).isNotNull();
        Assertions.assertThat(hero.getName()).isEqualTo("Arus");
        Assertions.assertThat(hero.getLevel()).isEqualTo(1);
        Assertions.assertThat(hero.getExperience()).isEqualTo(0);

        Hero savedHero = heroRepo.findById(hero.getId()).orElse(null);
        Assertions.assertThat(savedHero).isNotNull();
        Assertions.assertThat(savedHero.getName()).isEqualTo("Arus");
    }

    @Test
    @DisplayName("PostgreSQLを使用して経験値を取得しレベルが上がる")
    void givenHeroInDatabase_whenHeroDefeatedMonster_thenHeroLevelIsUpdated() {
        Hero hero = heroService.getHero(1);
        hero.setExperience(90);
        heroRepo.save(hero);

        heroService.heroDefeatedMonster(hero, 20);

        Hero updatedHero = heroRepo.findById(hero.getId()).orElse(null);
        Assertions.assertThat(updatedHero).isNotNull();
        Assertions.assertThat(updatedHero.getLevel()).isEqualTo(2);
        Assertions.assertThat(updatedHero.getExperience()).isEqualTo(110);
    }
}
