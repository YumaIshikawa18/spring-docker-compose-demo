package com.yuzukiku.dockerquest.presentation;

import com.yuzukiku.dockerquest.application.HeroService;
import com.yuzukiku.dockerquest.domain.entity.Hero;
import com.yuzukiku.dockerquest.domain.entity.Monster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HeroConsumer {

    private static final Logger logger = LoggerFactory.getLogger(HeroConsumer.class);

    private final HeroService heroService;

    public HeroConsumer(HeroService heroService) {
        this.heroService = heroService;
    }

    @KafkaListener(topics = "monster-topic", groupId = "hero-group")
    public void consume(Monster monster) {
        Hero hero = heroService.getHero(1);
        int gainedExperience = monster.getExperiencePoints();

        logger.debug("Monster received: {} with {} EXP points", monster.monsters().name(), gainedExperience);

        heroService.heroDefeatedMonster(hero, gainedExperience);

        logger.debug("Hero defeated {} and gained {} EXP. Current level: {}",
                monster.monsters().name(), gainedExperience, hero.getLevel());
    }
}
