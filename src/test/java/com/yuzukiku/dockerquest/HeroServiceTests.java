package com.yuzukiku.dockerquest;

import com.yuzukiku.dockerquest.application.HeroService;
import com.yuzukiku.dockerquest.domain.entity.Hero;
import com.yuzukiku.dockerquest.domain.repository.HeroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.Optional;

public class HeroServiceTests {

    private final HeroRepository heroRepository = Mockito.mock(HeroRepository.class);
    private final HeroService heroService = new HeroService(heroRepository);

    @Test
    @DisplayName("勇者が存在しない場合、新たな勇者が誕生する")
    void givenNonExistingHero_whenGetHero_thenNewHeroIsCreated() {
        Mockito.when(heroRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(heroRepository.save(Mockito.any(Hero.class)))
                .thenAnswer(invocation -> {
                    Hero hero = invocation.getArgument(0);
                    hero.setId(1);
                    return hero;
                });

        Hero hero = heroService.getHero(1);

        Mockito.verify(heroRepository).save(Mockito.any(Hero.class));
        Assertions.assertThat(hero).isNotNull();
        Assertions.assertThat(hero.getName()).isEqualTo("Arus");
        Assertions.assertThat(hero.getId()).isEqualTo(1);
        Assertions.assertThat(hero.getExperience()).isZero();
    }

    @Test
    @DisplayName("勇者が存在する場合、その勇者を召喚する")
    void givenExistingHero_whenGetHero_thenHeroIsReturned() {
        Hero existingHero = new Hero("Roto");
        existingHero.setId(1);
        Mockito.when(heroRepository.findById(1)).thenReturn(Optional.of(existingHero));

        Hero hero = heroService.getHero(1);

        Assertions.assertThat(hero).isEqualTo(existingHero);
        Mockito.verify(heroRepository, Mockito.never()).save(Mockito.any(Hero.class));
    }

    @ParameterizedTest
    @CsvSource(delimiterString = "|", textBlock = """
            1| 100| 2
            2| 200| 5
            3| 500| 10
            """)
    @DisplayName("経験値を取得し、レベルが正しく上がる")
    void givenHeroAndExperience_whenHeroDefeatedMonster_thenLevelIsUpdated(
            int initialLevel,
            int experience,
            int expectedLevel
    ) {
        Hero hero = new Hero("Eleven");
        hero.setLevel(initialLevel);
        hero.setExperience(0);

        heroService.heroDefeatedMonster(hero, experience);

        Assertions.assertThat(hero.getLevel()).isEqualTo(expectedLevel);
        Assertions.assertThat(hero.getExperience()).isGreaterThan(0);
        Mockito.verify(heroRepository).save(hero);
    }

    @Test
    @DisplayName("経験値を取得しても次のレベルまでの経験値に至っていない場合")
    void givenHeroAndLowExperience_whenHeroDefeatedMonster_thenLevelRemainsSame() {
        Hero hero = new Hero("X");
        hero.setLevel(1);
        hero.setExperience(50);

        heroService.heroDefeatedMonster(hero, 20);

        Assertions.assertThat(hero.getLevel()).isEqualTo(1);
        Assertions.assertThat(hero.getExperience()).isEqualTo(70);
        Mockito.verify(heroRepository).save(hero);
    }
}
