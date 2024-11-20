package com.yuzukiku.dockerquest.domain.entity;

/**
 * Dragon Quest 3 Monsters
 */
public enum Monsters {
    SLIME(4),               // スライム
    LAGHING_SACK(5),        // わらいぶくろ
    MAN_FACED_BUTTERFLY(6), // じんめんちょう
    BIG_CROW(6),            // おおがらす
    SLIME_BETH( 8),         // スライムベス
    FROGER(12),             // フロッガー
    WIZARD(20),             // まほうつかい
    HOIMI_SLIME(24),        // ホイミスライム
    GIZMO(35),              // ギズモ
    GROUP_CRAB(35),         // ぐんたいガニ
    VIOLENT_MONKEY(60),     // あばれザル
    VAMPIRE(61),            // バンパイア
    WANDERING_ARMOR(68),    // さまようよろい
    MUMMY_MAN(73),          // ミイラ男
    BABY_SATAN(100),        // ベビーサタン
    ROTTEN_CORPSE(120),     // くさったしたい
    MURDERER(150),          // さつじんき
    SLIME_SNAIL(210),       // スライムつむり
    GARUDA(220),            // ガルーダ
    WITCH(305),             // まじょ
    GIANT_SQUID(355),       // だいおうイカ
    GOLD_MAN(390),          // ゴールドマン
    GRIZZLY(523),           // グリズリー
    GAMEGON(652),           // ガメゴン
    MUD_HAND(720),          // マドハンド
    TROLL(1030),            // トロル
    CHIMERA(1780),          // キメラ
    SKULLGON(2350),         // スカルゴン
    DRAGON(2600),           // ドラゴン
    HYDRA(3090),            // ヒドラ
    PHOENIX(3700),          // ほうおう
    METAL_SLIME(4140),      // メタルスライム
    SALAMANDER(6000),       // サラマンダー
    BARAMOTH_EVIL(7300),    // バラモスエビル
    LONE_METAL_SLIME(40200);// はぐれメタル

    private final int experiencePoints;

    Monsters(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }
}
