package com.yuzukiku.dockerquest.presentation;

import com.yuzukiku.dockerquest.application.HeroService;
import com.yuzukiku.dockerquest.domain.entity.Hero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/hero")
    public Hero getHero() {
        return heroService.getHero(1);
    }
}
