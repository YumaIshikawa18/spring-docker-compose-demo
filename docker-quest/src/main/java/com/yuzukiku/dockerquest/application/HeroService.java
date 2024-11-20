package com.yuzukiku.dockerquest.application;

import com.yuzukiku.dockerquest.domain.entity.Hero;
import com.yuzukiku.dockerquest.domain.repository.HeroRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HeroService {

    private static final Logger logger = LoggerFactory.getLogger(HeroService.class);

    private final HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero getHero(Integer id) {
        return heroRepository.findById(id)
                .orElseGet(() ->  {
                    Hero newHero = new Hero("Arus"); // DQ3 Main Character
                    heroRepository.save(newHero);
                    logger.debug("New hero created with name: {}", newHero.getName());
                    return newHero;
                });
    }

    public void heroDefeatedMonster(Hero hero, int experiencePoints) {
        hero.gainExperience(experiencePoints);
        int requiredExperience = calculateRequiredExperience(hero.getLevel());
        int remainingExperience = requiredExperience - hero.getExperience();

        logger.debug("Hero defeated a monster! Gained ExperiencePoints: {}. Total Experience: {}. Remaining Experience for next level: {}",
                experiencePoints, hero.getExperience(), Math.max(remainingExperience, 0));

        levelUpHero(hero);
        heroRepository.save(hero);
    }

    private void levelUpHero(Hero hero) {
        int requiredExperience = calculateRequiredExperience(hero.getLevel());

        while (hero.getExperience() >= requiredExperience) {
            hero.setLevel(hero.getLevel() + 1);
            logger.debug("Level up! Current level: {}", hero.getLevel());

            requiredExperience = calculateRequiredExperience(hero.getLevel());
        }
    }

    private int calculateRequiredExperience(int level) {
        int baseExperience = 100;
        double growthRate = 1.2;
        return (int) (baseExperience * Math.pow(growthRate, level - 1));
    }
}
