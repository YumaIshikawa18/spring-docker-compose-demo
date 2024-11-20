package com.yuzukiku.dockerquest.application;

import com.yuzukiku.dockerquest.domain.entity.Monster;
import com.yuzukiku.dockerquest.domain.entity.Monsters;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Transactional
public class MonsterService {

    private final SecureRandom random = new SecureRandom();

    public Monster generateRandomMonster() {
        Monsters[] monsters = Monsters.values();
        Monsters randomMonster = monsters[random.nextInt(monsters.length)];
        return new Monster(randomMonster);
    }
}
