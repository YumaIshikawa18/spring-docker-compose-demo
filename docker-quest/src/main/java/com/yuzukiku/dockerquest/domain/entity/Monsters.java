package com.yuzukiku.dockerquest.domain.entity;

public enum Monsters {
    SLIME(5),
    SHE_SLIME(7),
    BUBBLE_SLIME(10),
    GHOST(12),
    DRACKY(15),
    SKELETON(20),
    ZOMBIE(25),
    ORC(30),
    GOLEM(35),
    WYVERN(40),
    TROLL(45),
    DEMON(50),
    DRAGON(60),
    KING_SLIME(70),
    METAL_SLIME(100),
    KNIGHT(55),
    WITCH(65),
    SPECTER(75),
    VAMPIRE(80),
    BEHEMOTH(85),
    GRIFFIN(90),
    HYDRA(95),
    PHOENIX(100),
    CHIMERA(110),
    MINOTAUR(115),
    CYCLOPS(120),
    LICH(130),
    DARK_KNIGHT(140),
    ARCHDEMON(150),
    DRAGON_LORD(200);

    private final int experiencePoints;

    Monsters(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }
}
