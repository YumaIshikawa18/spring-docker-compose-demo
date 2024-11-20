package com.yuzukiku.dockerquest.domain.repository;

import com.yuzukiku.dockerquest.domain.entity.Hero;

import java.util.Optional;

public interface HeroRepository {
    Optional<Hero> findById(Integer id);
    Hero save(Hero hero);
}
