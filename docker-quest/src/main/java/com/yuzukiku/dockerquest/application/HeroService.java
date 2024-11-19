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
                    Hero newHero = new Hero("勇者");
                    heroRepository.save(newHero);
                    logger.debug("New hero created with name: {}", newHero.getName());
                    return newHero;
                });
    }

    public void heroDefeatedMonster(Hero hero, int experiencePoints) {
        hero.gainExperience(experiencePoints);
        logger.debug("Hero defeated with ExperiencePoints: {}", experiencePoints);

        levelUpHero(hero);
        heroRepository.save(hero);
    }

    private void levelUpHero(Hero hero) {
        int requiredExperience = calculateRequiredExperience(hero.getLevel());
        // 累積経験値が現在のレベルの必要経験値を超えている場合
        while (hero.getExperience() >= requiredExperience) {
            hero.setLevel(hero.getLevel() + 1); // レベルアップ
            logger.debug("Level up! Current level: {}", hero.getLevel());

            // 次のレベルの必要経験値を再計算
            requiredExperience = calculateRequiredExperience(hero.getLevel());
        }
    }

    private int calculateRequiredExperience(int level) {
        int baseExperience = 100;
        double growthRate = 1.2;
        return (int) (baseExperience * Math.pow(growthRate, level - 1));
    }
}
