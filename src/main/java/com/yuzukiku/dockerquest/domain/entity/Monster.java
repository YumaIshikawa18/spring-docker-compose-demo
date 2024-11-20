package com.yuzukiku.dockerquest.domain.entity;

public record Monster(Monsters monsters) {
    public int getExperiencePoints() {
        return monsters.getExperiencePoints();
    }
}
