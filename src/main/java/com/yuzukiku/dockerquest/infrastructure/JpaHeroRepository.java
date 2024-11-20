package com.yuzukiku.dockerquest.infrastructure;

import com.yuzukiku.dockerquest.domain.entity.Hero;
import com.yuzukiku.dockerquest.domain.repository.HeroRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaHeroRepository extends HeroRepository, JpaRepository<Hero, Long> {
}
